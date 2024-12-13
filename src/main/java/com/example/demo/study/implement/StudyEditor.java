package com.example.demo.study.implement;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

	private final CafeStudyRepository cafeStudyRepository;
	private final StudyRepository2 studyRepository2;

	private final StudyMemberEditor studyMemberEditor;
	private final StudyTagEditor studyTagEditor;

	private final StudyValidator studyValidator;

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

	@Transactional
	public void removeWithCascade(Long studyId, Long candidateCoordinatorId, LocalDateTime now) {
		CafeStudyEntity cafeStudy = cafeStudyRepository.findById(studyId)
			.orElseThrow(() -> new CafegoryException(CAFE_STUDY_NOT_FOUND));
		studyValidator.validateMemberIsCafeStudyCoordinator(candidateCoordinatorId, cafeStudy.getCoordinator().getId());

		studyMemberEditor.remove(studyId, candidateCoordinatorId, now);
		studyTagEditor.removeStudyStudyTagBy(studyId, now);
		cafeStudy.softDelete(now);
	}
}
