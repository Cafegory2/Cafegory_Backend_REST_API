package com.example.demo.member.domain;

import com.example.demo.domain.DateAudit;
import com.example.demo.implement.member.BeverageSize;
import com.example.demo.implement.member.Role;

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
}
