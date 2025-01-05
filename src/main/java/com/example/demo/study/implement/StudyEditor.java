package com.example.demo.study.implement;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.cafe.domain.CafeId;
import com.example.demo.member.domain.MemberId;
import com.example.demo.study.domain.Coordinator;
import com.example.demo.study.domain.Study;
import com.example.demo.study.domain.StudyContent;
import com.example.demo.study.domain.StudyId;
import com.example.demo.study.domain.StudyTagId;
import com.example.demo.study.infrastructure.repository2.CoordinatorRepository;
import com.example.demo.study.infrastructure.repository2.StudyMemberRepository;
import com.example.demo.study.infrastructure.repository2.StudyQueryRepository;
import com.example.demo.study.infrastructure.repository2.StudyRepository;
import com.example.demo.study.infrastructure.repository2.StudyStudyTagRepository;
import com.example.demo.study.infrastructure.repository2.StudyTagRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StudyEditor {

	private final StudyRepository studyRepository;
	private final StudyQueryRepository studyQueryRepository;

	private final StudyTagRepository studyTagRepository;
	private final StudyStudyTagRepository studyStudyTagRepository;
	private final StudyMemberRepository studyMemberRepository;
	private final CoordinatorRepository coordinatorRepository;

	private final StudyValidator studyValidator;

	@Transactional
	public StudyId saveWithCascade(StudyContent content, CafeId cafeId, MemberId memberId) {
		validateStudyDetails(content);

		StudyId savedStudyId = studyRepository.save(content, cafeId, memberId);
		List<StudyTagId> studyTagIds = studyTagRepository.countByTags(content.getTags());
		studyStudyTagRepository.saveAll(savedStudyId, studyTagIds);

		return savedStudyId;
	}

	private void validateStudyDetails(StudyContent content) {
		studyValidator.validateEmptyOrWhiteSpace(content.getName(), STUDY_ONCE_NAME_EMPTY_OR_WHITESPACE);
		studyValidator.validateNameLength(content.getName());
		studyValidator.validateMaxParticipants(content.getMaxParticipantCount());
	}

	@Transactional
	public void removeWithCascade(StudyId studyId, MemberId candidateCoordinatorId, LocalDateTime now) {
		Study study = studyQueryRepository.findById(studyId);

		List<Coordinator> coordinators = coordinatorRepository.findBy(candidateCoordinatorId);

		studyValidator.validateCoordinatorIsInStudy(study, coordinators);

		studyMemberRepository.remove(studyId, candidateCoordinatorId, now);
		studyStudyTagRepository.remove(studyId, now);

		studyRepository.deleteWithCascade(studyId, candidateCoordinatorId, now);
	}
}
