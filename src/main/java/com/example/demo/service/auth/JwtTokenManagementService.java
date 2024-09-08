package com.example.demo.service.auth;

import static com.example.demo.exception.ExceptionType.*;
import static com.example.demo.implement.tokenmanagerment.TokenClaims.*;

import java.util.Map;

import com.example.demo.implement.tokenmanagerment.JwtCafegoryTokenManager;
import com.example.demo.implement.tokenmanagerment.JwtTokenManager;
import com.example.demo.implement.token.JwtAccessToken;
import com.example.demo.implement.token.JwtClaims;
import com.example.demo.exception.ExceptionType;
import com.example.demo.exception.JwtCustomException;
import com.example.demo.implement.member.Member;
import com.example.demo.repository.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.example.demo.implement.token.JwtToken;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtTokenManagementService {

    private final JwtCafegoryTokenManager jwtCafegoryTokenManager;
    private final JwtTokenManager jwtTokenManager;
    private final MemberRepository memberRepository;

    public JwtToken createAccessAndRefreshToken(final Long memberId) {
        return jwtCafegoryTokenManager.createAccessAndRefreshToken(
            Map.of(SUBJECT.getValue(), String.valueOf(memberId))
        );
    }

    public JwtAccessToken verifyAndRefreshAccessToken(final String accessToken, final String refreshToken) {
        //TODO 토큰 재발급 API는 토큰 검증 인터셉터를 거치면 안된다. 토큰 검증 인터셉터는 액세스 토큰의 만료를 검증한다.
        validateNullToken(accessToken, ExceptionType.JWT_ACCESS_TOKEN_MISSING);
        validateNullToken(refreshToken, ExceptionType.JWT_REFRESH_TOKEN_MISSING);

        JwtClaims accessTokenClaims = verifyAndExtractAccessClaims(accessToken);
        JwtClaims refreshTokenClaims = verifyAndExtractRefreshClaims(refreshToken);

        validateTokenSubjectMatch(accessTokenClaims, refreshTokenClaims);

        Long memberIdInClaim = Long.parseLong(refreshTokenClaims.getSubject());
        Member memberInDb = memberRepository.findById(memberIdInClaim)
                .orElseThrow(() -> new JwtCustomException(JWT_REFRESH_MEMBER_NOT_FOUND, refreshTokenClaims));

        validateMemberIdMatches(memberIdInClaim, memberInDb.getId(), refreshTokenClaims);

        return jwtCafegoryTokenManager.createAccessToken(convertMemberToMap(memberInDb));
    }

    private void validateNullToken(final String token, ExceptionType exceptionType) {
        if(token == null) {
            throw new JwtCustomException(exceptionType);
        }
    }



    private void validateMemberIdMatches(final Long memberIdInClaim, final Long memberIdInDb, final JwtClaims refreshTokenClaims) {
        if (!memberIdInClaim.equals(memberIdInDb)) {
            throw new JwtCustomException(JWT_REFRESH_MEMBER_ID_MISMATCH, refreshTokenClaims);
        }
    }

    private void validateTokenSubjectMatch(final JwtClaims accessTokenClaims, final JwtClaims refreshTokenClaims) {
        String accessTokenSubject = accessTokenClaims.getClaim(SUBJECT.getValue());
        String refreshTokenSubject = refreshTokenClaims.getClaim(SUBJECT.getValue());

        if(!accessTokenSubject.equals(refreshTokenSubject)) {
            throw new JwtCustomException(JWT_ACCESS_SUB_AND_REFRESH_SUB_NOT_MATCHED);
        }
    }

    private JwtClaims verifyAndExtractRefreshClaims(final String refreshToken) {
        jwtTokenManager.validateClaim(refreshToken, TOKEN_TYPE.getValue(), REFRESH_TOKEN.getValue());
        return jwtTokenManager.verifyAndExtractClaims(refreshToken);
    }

    private JwtClaims verifyAndExtractAccessClaims(final String accessToken) {
        try {
            return jwtTokenManager.verifyAndExtractClaims(accessToken);
        } catch (JwtCustomException e) {
            if (e.getExceptionType() == JWT_EXPIRED) {
                return e.getClaims();
            }
            throw e;
        }
    }

    private Map<String, Object> convertMemberToMap(Member member) {
        return Map.of(
                SUBJECT.getValue(), String.valueOf(member.getId()),
                ROLE.getValue(), member.getRole().getValue()
        );
    }
}