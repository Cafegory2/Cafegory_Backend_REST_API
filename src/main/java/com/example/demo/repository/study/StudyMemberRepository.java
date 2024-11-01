package com.example.demo.repository.study;

import java.util.List;

import com.example.demo.member.infrastructure.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.implement.study.CafeStudyMemberEntity;
import com.example.demo.implement.study.StudyMemberId;
import com.example.demo.member.domain.Member;

public interface StudyMemberRepository
	extends JpaRepository<CafeStudyMemberEntity, StudyMemberId>, StudyMemberRepositoryCustom {

	List<CafeStudyMemberEntity> findByMember(MemberEntity member);
}
