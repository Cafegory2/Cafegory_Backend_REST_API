package com.example.demo.spy;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.CafegoryException;
import com.example.demo.member.domain.Member;
import com.example.demo.member.domain.MemberContent;
import com.example.demo.member.domain.MemberId;
import com.example.demo.member.implement.MemberEditor;
import com.example.demo.member.implement.MemberReader;
import com.example.demo.auth.dto.oauth2.OAuth2Profile;
import com.example.demo.auth.dto.oauth2.OAuth2TokenRequest;
import com.example.demo.auth.implement.login.LoginProcessor;
import com.example.demo.auth.implement.signup.SignupProcessor;
import com.example.demo.auth.implement.token.JwtToken;
import com.example.demo.auth.service.login.LoginService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SpyLoginService implements LoginService {

	private final MemberReader memberReader;

	private final LoginProcessor loginProcessor;
	private final SignupProcessor signupProcessor;
	private final MemberEditor memberEditor;

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
		memberEditor.updateRefreshToken(new MemberId(member.getId().getId()), token.getRefreshToken());

		String filename = UUID.randomUUID().toString();
		memberEditor.edit(
			MemberContent.builder()
				.imgUrl(filename)
				.build()
			, new MemberId(member.getId().getId())
		);
		//		member.changeProfileUrl(filename);

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
