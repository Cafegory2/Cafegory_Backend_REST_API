package com.example.demo.domain.member.service;

import org.springframework.stereotype.Service;

import com.example.demo.domain.member.domain.Member;
import com.example.demo.domain.member.domain.MemberContent;
import com.example.demo.domain.member.domain.MemberId;
import com.example.demo.domain.member.implement.MemberReader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {

	private final MemberReader memberReader;

	public MemberContent getWelcomeProfile(MemberId memberId) {
		Member member = memberReader.read(memberId);
		return member.getContent();
	}
}
