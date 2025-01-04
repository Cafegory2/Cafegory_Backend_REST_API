package com.example.demo.member.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {

	Optional<MemberEntity> findByEmail(String email);

	boolean existsByEmail(String email);
}
