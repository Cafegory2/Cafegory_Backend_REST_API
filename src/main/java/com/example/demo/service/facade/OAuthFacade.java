package com.example.demo.service.facade;

import com.example.demo.domain.member.Member;
import com.example.demo.dto.auth.CafegoryToken;
import com.example.demo.dto.oauth2.OAuth2Profile;
import com.example.demo.dto.oauth2.OAuth2TokenRequest;
import com.example.demo.exception.CafegoryException;
import com.example.demo.exception.ExceptionType;
import com.example.demo.repository.member.MemberRepository;
import com.example.demo.service.auth.JwtService;
import com.example.demo.service.member.MemberService;
import com.example.demo.service.oauth2.OAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OAuthFacade {

    private final OAuth2Service oAuth2Service;
    private final MemberService memberService;
    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    @Transactional
    public CafegoryToken handleOauthLogin(OAuth2TokenRequest oAuth2TokenRequest) {
        OAuth2Profile profile = oAuth2Service.fetchMemberProfile(oAuth2TokenRequest);

        Long memberId = memberService.findOrCreateMember(
                profile.getEmailAddress(),
                profile.getNickName(),
                profile.getProfileImgUrl()
        );

        CafegoryToken token = jwtService.createAccessAndRefreshToken(memberId);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CafegoryException(ExceptionType.MEMBER_NOT_FOUND));
        member.setRefreshToken(token.getRefreshToken());

        return token;
    }
}
