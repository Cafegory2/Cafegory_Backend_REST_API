package com.example.demo.study.implement;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.CafegoryException;
import com.example.demo.member.domain.MemberId;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.study.domain.Participant;
import com.example.demo.study.domain.StudyId;
import com.example.demo.study.domain.StudyRole;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.study.infrastructure.CafeStudyMemberEntity;
import com.example.demo.study.infrastructure.StudyMemberRepository;
import com.example.demo.study.infrastructure.repository2.StudyMemberRepository2;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StudyMemberEditor {

	private final StudyMemberRepository studyMemberRepository;
	private final StudyMemberRepository2 studyMemberRepository2;

	public Long save(Long memberId, Long studyId, StudyRole studyRole) {
		Participant participant = studyMemberRepository2.save(memberId, studyId, studyRole);

		return participant.getId();
	}

	public Long save(MemberId memberId, StudyId studyId, StudyRole studyRole) {
		Participant participant = studyMemberRepository2.save(memberId, studyId, studyRole);

		return participant.getId();
	}

	private CafeStudyMemberEntity createStudyMember(
		MemberEntity member, CafeStudyEntity cafeStudy, StudyRole studyRole) {
		return CafeStudyMemberEntity.builder()
			.cafeStudy(cafeStudy)
			.member(member)
			.studyRole(studyRole)
			.build();
	}

	@Transactional
	public void remove(Long studyId, Long memberId, LocalDateTime now) {
		CafeStudyMemberEntity studyMemberEntity = studyMemberRepository.findByCafeStudy_IdAndMember_Id(studyId,
				memberId)
			.orElseThrow(() -> new CafegoryException(STUDY_MEMBER_NOT_FOUND));

		studyMemberEntity.softDelete(now);
	}
}
