package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.StudyOnceQuestion;

public interface StudyOnceQuestionRepository extends JpaRepository<StudyOnceQuestion, Long> {
}
