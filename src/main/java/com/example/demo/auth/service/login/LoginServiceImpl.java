package com.example.demo.auth.service.login;

import java.util.UUID;

import org.springframework.stereotype.Service;
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
import com.example.demo.auth.infrastructure.aws.AwsS3Client;
import com.example.demo.auth.infrastructure.oauth2.OAuth2Client;
import com.example.demo.util.ImageData;
import com.example.demo.util.ImageDownloadUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

	private final OAuth2Client oAuth2Client;
	private final AwsS3Client awsS3Client;

	private final MemberReader memberReader;
	private final MemberEditor memberEditor;

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
		Member member = memberReader.read(profile.getEmailAddress());

		String profileUrl = uploadProfileImageToS3(profile.getProfileImgUrl());

		memberEditor.updateRefreshToken(new MemberId(member.getId().getId()), token.getRefreshToken());
		memberEditor.edit(createMemberContent(profileUrl), new MemberId(member.getId().getId()));
	}

	private MemberContent createMemberContent(String imgUrl) {
		return MemberContent.builder()
			.imgUrl(imgUrl)
			.build();
	}

	private String uploadProfileImageToS3(String imageUrl) {
		ImageData imageData = ImageDownloadUtil.downloadImage(imageUrl);
		String filename = UUID.randomUUID().toString();

		awsS3Client.uploadImageToS3(filename, imageData);
		return awsS3Client.getUrl(filename);
	}
}
