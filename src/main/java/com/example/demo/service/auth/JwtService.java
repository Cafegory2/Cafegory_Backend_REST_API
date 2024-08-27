package com.example.demo.service.auth;

import static com.example.demo.exception.ExceptionType.*;

import java.util.Map;

import com.example.demo.domain.auth.JwtCafegoryTokenManager;
import com.example.demo.domain.auth.TokenClaims;
import com.example.demo.domain.member.Member;
import com.example.demo.repository.member.MemberRepository;
import org.springframework.stereotype.Service;

import com.example.demo.dto.auth.CafegoryToken;
import com.example.demo.exception.CafegoryException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtCafegoryTokenManager jwtCafegoryTokenManager;
    private final MemberRepository memberRepository;

//    public CafegoryToken refreshCafegoryToken(final String accessToken, final String refreshToken) {
//        boolean canRefresh = jwtCafegoryTokenManager.canRefresh(refreshToken);
//        if (canRefresh) {
//            long identityId = jwtCafegoryTokenManager.getIdentityId(refreshToken);
//            return makeCafegoryToken(identityId);
//        }
//        throw new CafegoryException(TOKEN_REFRESH_REJECT);
//    }

    public CafegoryToken createAccessToken(final Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CafegoryException(MEMBER_NOT_FOUND));

        Map<String, Object> memberInfo = Map.of(
                TokenClaims.SUBJECT.getValue(), member.getId(),
                TokenClaims.ROLE.getValue(), member.getRole().getValue()
        );
        return jwtCafegoryTokenManager.createToken(memberInfo);
    }
}
