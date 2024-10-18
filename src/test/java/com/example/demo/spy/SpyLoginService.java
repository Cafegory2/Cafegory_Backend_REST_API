package com.example.demo.spy;

import com.example.demo.dto.oauth2.OAuth2Profile;
import com.example.demo.dto.oauth2.OAuth2TokenRequest;
import com.example.demo.exception.CafegoryException;
import com.example.demo.implement.login.LoginProcessor;
import com.example.demo.implement.member.Member;
import com.example.demo.packageex.member.implement.MemberReader;
import com.example.demo.implement.signup.SignupProcessor;
import com.example.demo.implement.token.JwtToken;
import com.example.demo.service.login.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
public class SpyLoginService implements LoginService {

    private final MemberReader memberReader;

    private final LoginProcessor loginProcessor;
    private final SignupProcessor signupProcessor;

    @Transactional
    public JwtToken socialLogin(OAuth2TokenRequest oAuth2TokenRequest) {
        OAuth2Profile profile = new OAuth2Profile() {
            @Override
            public String getNickName() {
                return "testNickname";
            }
            @Override
            public String getProfileImgUrl() {
                return "testProfileImgUrl";
            }

            @Override
            public String getEmailAddress() {
                return "test@gmail.com";
            }
        };

        JwtToken token = loginOrSignup(profile);

        Member member = memberReader.read(profile.getEmailAddress());
        member.setRefreshToken(token.getRefreshToken());

        String filename = UUID.randomUUID().toString();
        member.changeProfileUrl(filename);

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
}
