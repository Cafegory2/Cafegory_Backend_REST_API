package com.example.demo.db.member.repository2;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.db.member.MemberEntity;
import com.example.demo.db.member.MemberJpaRepository;
import com.example.demo.domain.member.domain.Member;

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
