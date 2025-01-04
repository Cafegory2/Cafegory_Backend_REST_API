package com.example.demo.study.infrastructure.repository2;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

import com.example.demo.member.domain.MemberId;
import com.example.demo.study.domain.StudyId;
import com.example.demo.study.domain.StudyMemberId;
import com.example.demo.study.domain.StudyRole;
import com.example.demo.study.infrastructure.CafeStudyMemberEntity;
import com.example.demo.study.infrastructure.StudyMemberRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StudyMemberRepositoryImpl2 implements StudyMemberRepository2 {

	private final StudyMemberRepository studyMemberJpaRepository;

	@Override
	public StudyMemberId save(MemberId memberId, StudyId studyId, StudyRole studyRole) {
		Long participantId = studyMemberJpaRepository.save(
			new CafeStudyMemberEntity(studyId.getId(), memberId.getId())
		).getId();

		return new StudyMemberId(participantId);
	}

	@Override
	public void remove(StudyId studyId, MemberId memberId, LocalDateTime now) {
		studyMemberJpaRepository.findByCafeStudy_IdAndMember_Id(studyId.getId(), memberId.getId())
			.orElseThrow(() -> new IllegalArgumentException("회원이 참여중인 스터디가 존재하지 않습니다."))
			.softDelete(now);
	}

}
