package com.example.demo.service.facade;

import com.example.demo.dto.auth.CafegoryToken;
import com.example.demo.dto.oauth2.OAuth2Profile;
import com.example.demo.dto.oauth2.OAuth2TokenRequest;
import com.example.demo.exception.CafegoryException;
import com.example.demo.exception.ExceptionType;
import com.example.demo.implement.member.Member;
import com.example.demo.repository.member.MemberRepository;
import com.example.demo.service.auth.JwtService;
import com.example.demo.service.aws.AwsService;
import com.example.demo.service.member.MemberService;
import com.example.demo.service.oauth2.OAuth2Service;
import com.example.demo.util.ImageData;
import com.example.demo.util.ImageDownloadUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuthFacade {

    private static final Logger log = LoggerFactory.getLogger(OAuthFacade.class);
    private final OAuth2Service oAuth2Service;
    private final MemberService memberService;
    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final AwsService awsService;

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

        ImageData imageData = ImageDownloadUtil.downloadImage(profile.getProfileImgUrl());
        awsService.uploadToS3(UUID.randomUUID().toString(), imageData);

        return token;
    }
}
