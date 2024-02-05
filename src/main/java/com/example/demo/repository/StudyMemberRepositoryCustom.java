package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.domain.MemberImpl;
import com.example.demo.domain.StudyMember;

public interface StudyMemberRepositoryCustom {
	List<StudyMember> findByMemberAndStudyDate(MemberImpl member, LocalDate studyDate);
}
