package com.example.demo.member.infrastructure.repository2;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.member.domain.Member;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.member.infrastructure.MemberRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepositoryImpl2 implements MemberQueryRepository2 {

	private final MemberRepository memberRepository;

	@Override
	public Optional<Member> findByEmail(String email) {
		return memberRepository.findByEmail(email).map(MemberEntity::toMember);
	}

	@Override
	public Optional<Member> findById(Long id) {
		return memberRepository.findById(id).map(MemberEntity::toMember);
	}
}
