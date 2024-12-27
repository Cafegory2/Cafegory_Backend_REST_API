package com.example.demo.member.domain;

import com.example.demo.domain.DateAudit;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Member {

	private Long id;
	private MemberContent content;
	private Role role;
	private String email;
	private String bio;
	private BeverageSize beverageSize;
	private DateAudit dateAudit;
	private String refreshToken;
}
