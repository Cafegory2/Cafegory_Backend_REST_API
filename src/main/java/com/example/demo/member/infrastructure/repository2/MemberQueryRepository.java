package com.example.demo.member.infrastructure.repository2;

import java.util.Optional;

import com.example.demo.member.domain.Member;

public interface MemberQueryRepository {

	Optional<Member> findByEmail(String email);

	Optional<Member> findById(Long id);
}
