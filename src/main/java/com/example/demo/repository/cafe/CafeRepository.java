package com.example.demo.repository.cafe;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.CafeImpl;

public interface CafeRepository extends JpaRepository<CafeImpl, Long>, CafeRepositoryCustom {

	Optional<CafeImpl> findById(Long id);
}
