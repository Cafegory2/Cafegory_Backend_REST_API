package com.example.demo.member.infrastructure.repository2;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.member.domain.Member;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.member.infrastructure.MemberJpaRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepositoryImpl implements MemberQueryRepository {

	private final MemberJpaRepository memberJpaRepository;

	@Override
	public Optional<Member> findByEmail(String email) {
		return memberJpaRepository.findByEmail(email).map(MemberEntity::toMember);
	}

	@Override
	public Optional<Member> findById(Long id) {
		return memberJpaRepository.findById(id).map(MemberEntity::toMember);
	}
}
