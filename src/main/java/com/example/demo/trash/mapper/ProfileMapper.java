package com.example.demo.trash.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.member.presentation.WelcomeProfileResponse;

@Component
public class ProfileMapper {

	public WelcomeProfileResponse toWelcomeProfileResponse(MemberEntity member) {
		return new WelcomeProfileResponse(member.getNickname(), member.getProfileUrl());
	}
}
