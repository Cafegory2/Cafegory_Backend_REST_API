package com.example.demo.repository.study;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.member.Member;
import com.example.demo.domain.study.CafeStudyMember;
import com.example.demo.domain.study.StudyMemberId;

public interface StudyMemberRepository
	extends JpaRepository<CafeStudyMember, StudyMemberId>, StudyMemberRepositoryCustom {

	List<CafeStudyMember> findByMember(Member member);
}
