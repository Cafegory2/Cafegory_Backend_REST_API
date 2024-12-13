package com.example.demo.study.implement;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.demo.study.domain.Study;
import com.example.demo.study.infrastructure.repository2.StudyQueryRepository2;
import com.example.demo.study.infrastructure.repository2.StudyRepository2;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StudyEditor {

	private final StudyRepository2 studyRepository2;
	private final StudyQueryRepository2 studyQueryRepository2;

	private final StudyValidator studyValidator;

	// TODO: save할 때 카공장의 기존 스터디를 조회하는 로직에서 toStudy 메서드 사용하여 예외 발생
	public Long saveWithCascade(Study study, Long memberId) {
		validateStudyDetails(study);

		Study savedStudy = studyRepository2.saveWithCascade(study, memberId);
		return savedStudy.getId();
	}

	private void validateStudyDetails(Study study) {
		studyValidator.validateEmptyOrWhiteSpace(study.getName(), STUDY_ONCE_NAME_EMPTY_OR_WHITESPACE);
		studyValidator.validateNameLength(study.getName());
		studyValidator.validateMaxParticipants(study.getMaxParticipantCount());
	}

	public void removeWithCascade(Long studyId, Long candidateCoordinatorId, LocalDateTime now) {
		Study study = studyQueryRepository2.findById(studyId);
		studyValidator.validateMemberIsCafeStudyCoordinator(candidateCoordinatorId, study.getCoordinator().getId());

		studyRepository2.deleteWithCascade(studyId, candidateCoordinatorId, now);
	}
}
