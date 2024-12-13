package com.example.demo.study.implement;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.study.infrastructure.repository2.StudyQueryRepository2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.cafe.domain.Cafe;
import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.cafe.infrastructure.CafeRepository;
import com.example.demo.cafe.infrastructure.repository2.CafeRepository2;
import com.example.demo.exception.CafegoryException;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.member.infrastructure.MemberRepository;
import com.example.demo.study.domain.Study;
import com.example.demo.study.infrastructure.CafeStudyCafeStudyTagEntity;
import com.example.demo.study.infrastructure.CafeStudyCafeStudyTagRepository;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.study.infrastructure.CafeStudyRepository;
import com.example.demo.study.infrastructure.CafeStudyTagEntity;
import com.example.demo.study.infrastructure.CafeStudyTagRepository;
import com.example.demo.study.infrastructure.StudyPeriod;
import com.example.demo.study.infrastructure.repository2.StudyRepository2;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StudyEditor {

	private final StudyRepository2 studyRepository2;
	private final StudyQueryRepository2 studyQueryRepository2;

	private final StudyValidator studyValidator;

	// TODO 트랜잭션 제거?
	// TODO: save할 때 카공장의 기존 스터디를 조회하는 로직에서 toStudy 메서드 사용하여 예외 발생
	@Transactional
	public Long save(Study study, Long memberId) {
		validateStudyDetails(study);

		Study savedStudy = studyRepository2.save(study, memberId);
		return savedStudy.getId();
	}

	private void validateStudyDetails(Study study) {
		studyValidator.validateEmptyOrWhiteSpace(study.getName(), STUDY_ONCE_NAME_EMPTY_OR_WHITESPACE);
		studyValidator.validateNameLength(study.getName());
		studyValidator.validateMaxParticipants(study.getMaxParticipantCount());
	}

	//TODO 트랜잭션 제거?
	@Transactional
	public void removeWithCascade(Long studyId, Long candidateCoordinatorId, LocalDateTime now) {
		Study study = studyQueryRepository2.findById(studyId);
		studyValidator.validateMemberIsCafeStudyCoordinator(candidateCoordinatorId, study.getCoordinator().getId());

		studyRepository2.deleteWithCascade(studyId, candidateCoordinatorId, now);
	}
}
