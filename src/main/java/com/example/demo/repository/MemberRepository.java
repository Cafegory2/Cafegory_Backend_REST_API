package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.MemberImpl;

public interface MemberRepository extends JpaRepository<MemberImpl, Long> {
	Optional<MemberImpl> findByEmail(String email);
}
