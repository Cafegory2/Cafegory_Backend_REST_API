package com.example.demo.service.facade;

import com.example.demo.implement.token.JwtToken;
import com.example.demo.dto.oauth2.OAuth2Profile;
import com.example.demo.dto.oauth2.OAuth2TokenRequest;
import com.example.demo.exception.CafegoryException;
import com.example.demo.implement.member.Member;
import com.example.demo.implement.member.Role;
import com.example.demo.repository.member.MemberRepository;
import com.example.demo.service.auth.JwtTokenService;
import com.example.demo.service.aws.AwsService;
import com.example.demo.service.oauth2.OAuth2Service;
import com.example.demo.util.ImageData;
import com.example.demo.util.ImageDownloadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.example.demo.exception.ExceptionType.*;

@Service
@RequiredArgsConstructor
public class OAuthFacade {

    private final OAuth2Service oAuth2Service;
    private final JwtTokenService jwtTokenService;
    private final MemberRepository memberRepository;
    private final AwsService awsService;

    @Transactional
    public JwtToken handleOauthLogin(OAuth2TokenRequest oAuth2TokenRequest) {
        OAuth2Profile profile = oAuth2Service.fetchMemberProfile(oAuth2TokenRequest);
        String email = profile.getEmailAddress();

        Member member;
        if(memberRepository.existsByEmail(email)) {
            member = findMember(email);
        } else {
            member = saveMember(email, profile.getNickName());
        }

        JwtToken token = jwtTokenService.createAccessAndRefreshToken(member.getId());
        member.setRefreshToken(token.getRefreshToken());

        ImageData imageData = ImageDownloadUtil.downloadImage(profile.getProfileImgUrl());
        String filename = UUID.randomUUID().toString();

        awsService.uploadImageToS3(filename, imageData);
        String profileUrl = awsService.getUrl(filename);

        member.changeProfileUrl(profileUrl);

        return token;
    }

    private Member findMember(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new CafegoryException(MEMBER_NOT_FOUND));
    }

    private Member saveMember(String email, String nickname) {
        Member newMember = Member.builder()
                .email(email)
                .nickname(nickname)
                .profileUrl(null)
                .role(Role.USER)
                .build();
        return memberRepository.save(newMember);
    }
}
