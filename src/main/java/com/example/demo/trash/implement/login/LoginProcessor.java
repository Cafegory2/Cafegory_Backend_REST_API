package com.example.demo.trash.implement.login;

import static com.example.demo.trash.implement.tokenmanagerment.TokenClaims.*;

import java.util.Map;

import com.example.demo.member.domain.Member;
import org.springframework.stereotype.Component;

import com.example.demo.member.implement.MemberReader;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.trash.implement.token.JwtToken;
import com.example.demo.trash.implement.tokenmanagerment.JwtCafegoryTokenManager;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginProcessor {

	private final JwtCafegoryTokenManager jwtCafegoryTokenManager;
	private final MemberReader memberReader;

	public JwtToken login(String email) {
		Member member = memberReader.read2(email);

		return jwtCafegoryTokenManager.createAccessAndRefreshToken(
			Map.of(SUBJECT.getValue(), String.valueOf(member.getId()))
		);
	}
}
