package com.example.demo.member.service;

import org.springframework.stereotype.Service;

import com.example.demo.member.domain.Member;
import com.example.demo.member.domain.MemberContent;
import com.example.demo.member.domain.MemberId;
import com.example.demo.member.implement.MemberReader;

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
