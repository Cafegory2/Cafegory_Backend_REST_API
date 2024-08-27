package com.example.demo.service.facade;

import com.example.demo.dto.auth.CafegoryToken;
import com.example.demo.dto.oauth2.OAuth2Profile;
import com.example.demo.dto.oauth2.OAuth2TokenRequest;
import com.example.demo.service.auth.JwtService;
import com.example.demo.service.member.MemberService;
import com.example.demo.service.oauth2.OAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OauthFacade {

    private final OAuth2Service oAuth2Service;
    private final MemberService memberService;
    private final JwtService jwtService;

    @Transactional
    public CafegoryToken handleOauthLogin(OAuth2TokenRequest oAuth2TokenRequest) {
        OAuth2Profile profile = oAuth2Service.fetchMemberProfile(oAuth2TokenRequest);
        Long memberId = memberService.findOrCreateMember(
                profile.getEmailAddress(),
                profile.getNickName(),
                profile.getProfileImgUrl()
        );
        return jwtService.createAccessToken(memberId);
    }
}
