package com.example.demo.member.domain;

import com.example.demo.domain.DateAudit;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Member {

	private MemberIdentity identity;
	private Role role;
	private String nickname;
	private String email;
	private String bio;
	private BeverageSize beverageSize;
	private String imgUrl;
	private DateAudit dateAudit;
	private String refreshToken;

	public Long getId() {
		return identity.getId();
	}
}
