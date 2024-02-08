package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.StudyMember;
import com.example.demo.domain.StudyMemberId;

public interface StudyMemberRepository extends JpaRepository<StudyMember, StudyMemberId>, StudyMemberRepositoryCustom {
}
