package com.example.demo.repository.study;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.packageex.cafestudy.repository.CafeStudyEntity;

public interface CafeStudyRepository extends JpaRepository<CafeStudyEntity, Long> {
}
