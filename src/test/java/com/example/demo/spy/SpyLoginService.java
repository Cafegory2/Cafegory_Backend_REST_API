package com.example.demo.spy;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.CafegoryException;
import com.example.demo.member.implement.MemberReader;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.trash.dto.oauth2.OAuth2Profile;
import com.example.demo.trash.dto.oauth2.OAuth2TokenRequest;
import com.example.demo.trash.implement.login.LoginProcessor;
import com.example.demo.trash.implement.signup.SignupProcessor;
import com.example.demo.trash.implement.token.JwtToken;
import com.example.demo.trash.service.login.LoginService;

import lombok.RequiredArgsConstructor;

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

		MemberEntity member = memberReader.read(profile.getEmailAddress());
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
