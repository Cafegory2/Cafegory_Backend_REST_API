package com.example.demo.cafe.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeTagJpaRepository extends JpaRepository<CafeTagEntity, Long> {
}
