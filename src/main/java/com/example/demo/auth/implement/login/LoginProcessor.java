package com.example.demo.auth.implement.login;

import static com.example.demo.auth.implement.tokenmanagerment.TokenClaims.*;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.demo.member.domain.Member;
import com.example.demo.member.implement.MemberReader;
import com.example.demo.auth.implement.token.JwtToken;
import com.example.demo.auth.implement.tokenmanagerment.JwtCafegoryTokenManager;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginProcessor {

	private final JwtCafegoryTokenManager jwtCafegoryTokenManager;
	private final MemberReader memberReader;

	public JwtToken login(String email) {
		Member member = memberReader.read(email);

		return jwtCafegoryTokenManager.createAccessAndRefreshToken(
			Map.of(SUBJECT.getValue(), String.valueOf(member.getId().getId()))
		);
	}
}
