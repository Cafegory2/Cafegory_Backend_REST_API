package com.example.demo.repository.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.implement.member.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

	Optional<MemberEntity> findByEmail(String email);

	boolean existsByEmail(String email);
}
