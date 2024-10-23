package com.example.demo.service.login;

import com.example.demo.exception.CafegoryException;
import com.example.demo.implement.login.LoginProcessor;
import com.example.demo.implement.member.MemberReader;
import com.example.demo.implement.signup.SignupProcessor;
import com.example.demo.implement.token.JwtToken;
import com.example.demo.dto.oauth2.OAuth2Profile;
import com.example.demo.dto.oauth2.OAuth2TokenRequest;
import com.example.demo.implement.member.MemberEntity;
import com.example.demo.infrastructure.aws.AwsS3Client;
import com.example.demo.infrastructure.oauth2.OAuth2Client;
import com.example.demo.util.ImageData;
import com.example.demo.util.ImageDownloadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    private final OAuth2Client oAuth2Client;
    private final AwsS3Client awsS3Client;

    private final MemberReader memberReader;

    private final LoginProcessor loginProcessor;
    private final SignupProcessor signupProcessor;

    @Transactional
    public JwtToken socialLogin(OAuth2TokenRequest oAuth2TokenRequest) {
        OAuth2Profile profile = oAuth2Client.fetchMemberProfile(oAuth2TokenRequest);
        JwtToken token = loginOrSignup(profile);
        updateMemberRefreshTokenAndProfile(profile, token);

        return token;
    }

    private JwtToken loginOrSignup(OAuth2Profile profile) {
        try {
            return loginProcessor.login(profile.getEmailAddress());
        } catch (CafegoryException e) {
            signupProcessor.signup(profile.getEmailAddress(), profile.getNickName());
            return loginProcessor.login(profile.getEmailAddress());
        }
    }

    private void updateMemberRefreshTokenAndProfile(OAuth2Profile profile, JwtToken token) {
        MemberEntity member = memberReader.read(profile.getEmailAddress());
        member.setRefreshToken(token.getRefreshToken());

        String profileUrl = uploadProfileImageToS3(profile.getProfileImgUrl());
        member.changeProfileUrl(profileUrl);
    }

    private String uploadProfileImageToS3(String imageUrl) {
        ImageData imageData = ImageDownloadUtil.downloadImage(imageUrl);
        String filename = UUID.randomUUID().toString();

        awsS3Client.uploadImageToS3(filename, imageData);
        return awsS3Client.getUrl(filename);
    }
}
