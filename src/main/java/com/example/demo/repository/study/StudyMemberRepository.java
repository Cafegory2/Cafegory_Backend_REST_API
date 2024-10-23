package com.example.demo.repository.study;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.implement.member.MemberEntity;
import com.example.demo.implement.study.CafeStudyMemberEntity;
import com.example.demo.implement.study.StudyMemberId;

public interface StudyMemberRepository
	extends JpaRepository<CafeStudyMemberEntity, StudyMemberId>, StudyMemberRepositoryCustom {

	List<CafeStudyMemberEntity> findByMember(MemberEntity member);
}
