package com.example.demo.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.domain.MemberImpl;
import com.example.demo.domain.ThumbnailImage;
import com.example.demo.domain.auth.CafegoryTokenManager;
import com.example.demo.domain.oauth2.KakaoOAuth2ProfileRequester;
import com.example.demo.domain.oauth2.KakaoOAuth2TokenRequester;
import com.example.demo.dto.auth.CafegoryToken;
import com.example.demo.dto.oauth2.OAuth2Profile;
import com.example.demo.dto.oauth2.OAuth2Token;
import com.example.demo.dto.oauth2.OAuth2TokenRequest;
import com.example.demo.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KakaoOAuth2Service implements OAuth2Service {
	private final KakaoOAuth2ProfileRequester oAuth2ProfileRequester;
	private final KakaoOAuth2TokenRequester oAuth2TokenRequester;
	private final CafegoryTokenManager cafegoryTokenManager;
	private final MemberRepository memberRepository;

	@Override
	public CafegoryToken joinOrLogin(OAuth2TokenRequest oAuth2TokenRequest) {
		OAuth2Profile oAuth2Profile = callOAuth2Api(oAuth2TokenRequest);
		Optional<MemberImpl> byEmail = memberRepository.findByEmail(oAuth2Profile.getEmailAddress());
		if (byEmail.isEmpty()) {
			MemberImpl save = memberRepository.save(makeNewMember(oAuth2Profile));
			return makeCafegoryToken(save);
		}
		return makeCafegoryToken(byEmail.get());
	}

	private CafegoryToken makeCafegoryToken(MemberImpl member) {
		Long memberId = member.getId();
		Map<String, String> claims = Map.of("memberId", String.valueOf(memberId));
		return cafegoryTokenManager.createToken(claims);
	}

	private static MemberImpl makeNewMember(OAuth2Profile oAuth2Profile) {
		ThumbnailImage thumbnailImage = new ThumbnailImage(null, oAuth2Profile.getProfileImgUrl());
		String nickName = oAuth2Profile.getNickName();
		String emailAddress = oAuth2Profile.getEmailAddress();
		return new MemberImpl(null, nickName, emailAddress, thumbnailImage);
	}

	private OAuth2Profile callOAuth2Api(OAuth2TokenRequest oAuth2TokenRequest) {
		OAuth2Token token = oAuth2TokenRequester.getToken(oAuth2TokenRequest);
		return oAuth2ProfileRequester.getOAuth2Profile(token);
	}
}
