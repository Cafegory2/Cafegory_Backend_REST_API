package com.example.demo.member.service;

import org.springframework.stereotype.Service;

import com.example.demo.member.domain.Member;
import com.example.demo.member.domain.MemberId;
import com.example.demo.member.implement.MemberReader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberReader memberReader;

	public Member getMember(MemberId memberId) {
		return memberReader.read(memberId);
	}
}
