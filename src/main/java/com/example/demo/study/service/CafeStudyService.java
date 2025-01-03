package com.example.demo.study.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.demo.cafe.domain.BusinessHour;
import com.example.demo.cafe.domain.Cafe;
import com.example.demo.cafe.implement.BusinessHourReader;
import com.example.demo.cafe.implement.BusinessHourValidator;
import com.example.demo.cafe.implement.CafeReader;
import com.example.demo.member.domain.MemberId;
import com.example.demo.study.domain.Study;
import com.example.demo.study.domain.StudyId;
import com.example.demo.study.domain.StudyRole;
import com.example.demo.study.implement.StudyEditor;
import com.example.demo.study.implement.StudyMemberEditor;
import com.example.demo.study.implement.StudyMemberReader;
import com.example.demo.study.implement.StudyReader;
import com.example.demo.study.implement.StudyValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CafeStudyService {
	private final StudyValidator studyValidator;
	private final BusinessHourValidator businessHourValidator;
	private final CafeReader cafeReader;
	private final BusinessHourReader businessHourReader;
	private final StudyReader studyReader;
	private final StudyEditor studyEditor;
	private final StudyMemberReader studyMemberReader;
	private final StudyMemberEditor studyMemberEditor;

	//TODO 카공 태그 저장하는 로직 추가 필요
	@Transactional
	public Long createStudy(Long memberId, LocalDateTime now, Study study) {
		validateStudyCreation(now, study.getSchedule().getStartDateTime());
		List<Study> participantStudies = studyReader.readUpcomingBy(memberId, now);
		studyValidator.validateStudyScheduleOverlap(study, participantStudies);

		Cafe cafe = cafeReader.read(study.getCafeId());
		BusinessHour businessHour = businessHourReader.readBy(cafe.getId(), study.getStartDate());
		businessHourValidator.validateBetweenBusinessHour(study.getSchedule(), businessHour);

		Long savedStudyId = studyEditor.saveWithCascade(study, memberId);
		studyMemberEditor.save(memberId, savedStudyId, StudyRole.COORDINATOR);

		return savedStudyId;
	}

	@Transactional
	public StudyId createStudy2(MemberId memberId, LocalDateTime now, Study study) {
		validateStudyCreation(now, study.getSchedule().getStartDateTime());
		List<Study> participantStudies = studyReader.readUpcomingBy(memberId.getId(), now);
		studyValidator.validateStudyScheduleOverlap(study, participantStudies);

		Cafe cafe = cafeReader.read(study.getCafeId());
		BusinessHour businessHour = businessHourReader.readBy(cafe.getId(), study.getStartDate());
		businessHourValidator.validateBetweenBusinessHour(study.getSchedule(), businessHour);

		StudyId savedStudyId = studyEditor.saveWithCascade2(study, memberId);
		studyMemberEditor.save(memberId, savedStudyId, StudyRole.COORDINATOR);

		return savedStudyId;
	}

	public void deleteStudy(Long memberId, Long cafeStudyId, LocalDateTime now) {
		Study study = studyReader.read(cafeStudyId);
		List<Long> participantIds = studyMemberReader.readParticipantIdsBy(cafeStudyId);
		studyValidator.validateCafeStudyMembersPresent(study, participantIds);

		studyEditor.removeWithCascade(study.getId(), memberId, now);
	}

	private void validateStudyCreation(LocalDateTime now, LocalDateTime startDateTime) {
		studyValidator.validateStartDateTime(now, startDateTime);
		studyValidator.validateStartDate(startDateTime);
	}
}
