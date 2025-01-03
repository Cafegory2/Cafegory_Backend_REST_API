package com.example.demo.study.infrastructure.repository2;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

import com.example.demo.exception.CafegoryException;
import com.example.demo.exception.ExceptionType;
import com.example.demo.member.domain.MemberId;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.member.infrastructure.MemberRepository;
import com.example.demo.study.domain.Participant;
import com.example.demo.study.domain.StudyId;
import com.example.demo.study.domain.StudyRole;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.study.infrastructure.CafeStudyMemberEntity;
import com.example.demo.study.infrastructure.CafeStudyRepository;
import com.example.demo.study.infrastructure.StudyMemberRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StudyMemberRepositoryImpl2 implements StudyMemberRepository2 {

	private final StudyMemberRepository studyMemberJpaRepository;
	private final MemberRepository memberJpaRepository;
	private final CafeStudyRepository studyJpaRepository;

	@Override
	public Participant save(Long memberId, Long studyId, StudyRole studyRole) {
		MemberEntity memberEntity = memberJpaRepository.findById(memberId)
			.orElseThrow(() -> new CafegoryException(ExceptionType.MEMBER_NOT_FOUND));

		CafeStudyEntity studyEntity = studyJpaRepository.findById(studyId)
			.orElseThrow(() -> new CafegoryException(ExceptionType.CAFE_STUDY_NOT_FOUND));

		CafeStudyMemberEntity studyMemberEntity = createStudyMember(memberEntity, studyEntity, studyRole);

		return studyMemberJpaRepository.save(studyMemberEntity)
			.toParticipant();
	}

	@Override
	public Participant save(MemberId memberId, StudyId studyId, StudyRole studyRole) {
		MemberEntity memberEntity = memberJpaRepository.findById(memberId.getId())
			.orElseThrow(() -> new CafegoryException(ExceptionType.MEMBER_NOT_FOUND));

		CafeStudyEntity studyEntity = studyJpaRepository.findById(studyId.getId())
			.orElseThrow(() -> new CafegoryException(ExceptionType.CAFE_STUDY_NOT_FOUND));

		CafeStudyMemberEntity studyMemberEntity = createStudyMember(memberEntity, studyEntity, studyRole);

		return studyMemberJpaRepository.save(studyMemberEntity)
			.toParticipant();
	}

	@Override
	public void remove(Long studyId, Long memberId, LocalDateTime now) {
		studyMemberJpaRepository.findByCafeStudy_IdAndMember_Id(studyId, memberId)
			.orElseThrow(() -> new CafegoryException(STUDY_MEMBER_NOT_FOUND))
			.softDelete(now);
	}

	private CafeStudyMemberEntity createStudyMember(
		MemberEntity memberEntity, CafeStudyEntity studyEntity, StudyRole studyRole) {
		return CafeStudyMemberEntity.builder()
			.cafeStudy(studyEntity)
			.member(memberEntity)
			.studyRole(studyRole)
			.build();
	}
}
