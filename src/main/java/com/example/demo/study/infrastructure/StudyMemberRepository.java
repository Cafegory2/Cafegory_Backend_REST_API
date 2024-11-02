package com.example.demo.study.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.member.infrastructure.MemberEntity;

public interface StudyMemberRepository extends JpaRepository<CafeStudyMemberEntity, StudyMemberId> {

	List<CafeStudyMemberEntity> findByMember_Id(Long memberId);

	int countByCafeStudy_Id(Long cafeStudyId);
}
