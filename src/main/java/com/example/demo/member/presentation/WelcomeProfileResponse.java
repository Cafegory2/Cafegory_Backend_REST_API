package com.example.demo.member.presentation;

import com.example.demo.member.domain.MemberContent;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class WelcomeProfileResponse {

	private String nickname;
	private String profileUrl;

	public static WelcomeProfileResponse of(MemberContent memberContent) {
		return new WelcomeProfileResponse(
			memberContent.getNickname(),
			memberContent.getImgUrl()
		);
	}
}
