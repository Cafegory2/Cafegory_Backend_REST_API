package com.example.demo.domain.member.service;

import org.springframework.stereotype.Service;

import com.example.demo.domain.member.domain.Member;
import com.example.demo.domain.member.domain.MemberId;
import com.example.demo.domain.member.implement.MemberReader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberReader memberReader;

	public Member getMember(MemberId memberId) {
		return memberReader.read(memberId);
	}
}
