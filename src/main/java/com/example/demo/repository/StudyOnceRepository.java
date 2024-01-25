package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.StudyOnceImpl;

public interface StudyOnceRepository extends JpaRepository<StudyOnceImpl, Long>, StudyOnceRepositoryCustom {
}
