package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.StudyOnceComment;

public interface StudyOnceCommentRepository extends JpaRepository<StudyOnceComment, Long> {
}
