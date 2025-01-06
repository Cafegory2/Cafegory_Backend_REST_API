package com.example.demo.domain.member.repository;

import java.util.Optional;

import com.example.demo.domain.member.domain.Member;

public interface MemberQueryRepository {

	Optional<Member> findByEmail(String email);

	Optional<Member> findById(Long id);
}
