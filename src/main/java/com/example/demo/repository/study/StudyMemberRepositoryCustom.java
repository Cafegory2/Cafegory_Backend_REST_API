package com.example.demo.repository.study;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.domain.member.Member;
import com.example.demo.domain.study.CafeStudyMember;

public interface StudyMemberRepositoryCustom {
	List<CafeStudyMember> findByMemberAndStudyDate(Member member, LocalDate studyDate);
}
