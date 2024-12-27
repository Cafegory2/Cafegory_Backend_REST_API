package com.example.demo.member.infrastructure.repository2;

import com.example.demo.member.domain.Member;

import java.util.Optional;

public interface MemberQueryRepository2 {

    Optional<Member> findByEmail(String email);
}
