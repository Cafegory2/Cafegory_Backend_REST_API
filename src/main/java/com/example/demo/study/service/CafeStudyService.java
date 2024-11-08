package com.example.demo.study.service;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.demo.cafe.domain.BusinessHour;
import com.example.demo.cafe.domain.Cafe;
import com.example.demo.cafe.implement.CafeReader;
import com.example.demo.exception.CafegoryException;
import com.example.demo.implement.cafe.BusinessHourReader;
import com.example.demo.member.implement.MemberReader;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.study.domain.Study;
import com.example.demo.study.domain.StudyRole;
import com.example.demo.study.implement.StudyEditor;
import com.example.demo.study.implement.StudyMemberEditor;
import com.example.demo.study.implement.StudyReader;
import com.example.demo.study.implement.StudyValidator;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.study.infrastructure.CafeStudyRepository;
import com.example.demo.validator.BusinessHourValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CafeStudyService {
	private final CafeStudyRepository cafeStudyRepository;
	private final StudyValidator studyValidator;
	private final BusinessHourValidator businessHourValidator;
	private final CafeReader cafeReader;
	private final BusinessHourReader businessHourReader;
	private final StudyReader cafeStudyReader;
	private final StudyEditor studyEditor;
	private final MemberReader memberReader;
	private final StudyReader studyReader;
	private final StudyMemberEditor studyMemberEditor;

	public CafeStudyEntity findCafeStudyById(Long cafeStudyId) {
		return cafeStudyRepository.findById(cafeStudyId).orElseThrow(() -> new CafegoryException(CAFE_STUDY_NOT_FOUND));
	}

	@Transactional
	public Study createStudy(Long memberId, LocalDateTime now, Study study) {
		validateStudyCreation(now, study.getSchedule().getStartDateTime());
		List<Study> participantStudies = studyReader.readUpcomingBy(memberId, now);
		studyValidator.validateStudyScheduleOverlap(study, participantStudies);

		Cafe cafe = cafeReader.read(study.getCafeId());
		BusinessHour businessHour = businessHourReader.readBy(cafe.getId(), study.getStartDate());
		businessHourValidator.validateBetweenBusinessHour(study.getSchedule(), businessHour);

		Long savedStudyId = studyEditor.save(study, cafe, memberId);
		studyMemberEditor.save(memberId, savedStudyId, StudyRole.COORDINATOR);

		return studyReader.read(savedStudyId);
	}

	@Transactional
	public Long deleteStudy(Long memberId, Long cafeStudyId, LocalDateTime now) {
//		CafeStudyEntity cafeStudy = cafeStudyReader.readStudyEntity(cafeStudyId);
		Study study = cafeStudyReader.read(cafeStudyId);
		MemberEntity member = memberReader.readMemberEntity(memberId);
		validateStudyDelete(member.getId(), study, cafeStudy); // cafeStudy대신 studyMemberIds 가 필요

		//TODO 구현계층으로 가야함
//		cafeStudy.softDelete(now);
		return cafeStudy.getId();
	}

	private void validateStudyCreation(LocalDateTime now, LocalDateTime startDateTime) {
		studyValidator.validateStartDateTime(now, startDateTime);
		studyValidator.validateStartDate(startDateTime);
	}

	private void validateStudyDelete(Long memberId, Study study, CafeStudyEntity cafeStudyEntity) {
		studyValidator.validateMemberIsCafeStudyCoordinator(memberId, study.getCoordinatorId());
		studyValidator.validateCafeStudyMembersPresent(study.getCoordinatorId(), cafeStudyEntity);
	}
}
