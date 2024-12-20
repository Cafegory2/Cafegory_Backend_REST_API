package com.example.demo.study.implement;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.study.domain.Study;
import com.example.demo.study.infrastructure.repository2.StudyMemberRepository2;
import com.example.demo.study.infrastructure.repository2.StudyQueryRepository2;
import com.example.demo.study.infrastructure.repository2.StudyRepository2;
import com.example.demo.study.infrastructure.repository2.StudyStudyTagRepository2;
import com.example.demo.study.infrastructure.repository2.StudyTagRepository2;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StudyEditor {

	private final StudyRepository2 studyRepository2;
	private final StudyQueryRepository2 studyQueryRepository2;

	private final StudyTagRepository2 studyTagRepository2;
	private final StudyStudyTagRepository2 studyStudyTagRepository2;
	private final StudyMemberRepository2 studyMemberRepository2;

	private final StudyValidator studyValidator;

	@Transactional
	public Long saveWithCascade(Study study, Long memberId) {
		validateStudyDetails(study);

		Long savedStudyId = studyRepository2.save(study, memberId);
		List<Long> studyTagIds = studyTagRepository2.countByTags(study.getTags());
		studyStudyTagRepository2.saveAll(savedStudyId, studyTagIds);

		return savedStudyId;
	}

	private void validateStudyDetails(Study study) {
		studyValidator.validateEmptyOrWhiteSpace(study.getName(), STUDY_ONCE_NAME_EMPTY_OR_WHITESPACE);
		studyValidator.validateNameLength(study.getName());
		studyValidator.validateMaxParticipants(study.getMaxParticipantCount());
	}

	@Transactional
	public void removeWithCascade(Long studyId, Long candidateCoordinatorId, LocalDateTime now) {
		Study study = studyQueryRepository2.findById(studyId);
		studyValidator.validateMemberIsCafeStudyCoordinator(candidateCoordinatorId, study.getCoordinator().getId());

		studyMemberRepository2.remove(studyId, candidateCoordinatorId, now);
		studyStudyTagRepository2.remove(studyId, now);

		studyRepository2.deleteWithCascade(studyId, candidateCoordinatorId, now);
	}
}
