package com.example.demo.repository.cafe;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.implement.cafe.Cafe;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
}
