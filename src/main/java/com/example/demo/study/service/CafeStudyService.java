package com.example.demo.study.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.demo.cafe.domain.BusinessHour;
import com.example.demo.cafe.domain.CafeId;
import com.example.demo.cafe.implement.BusinessHourReader;
import com.example.demo.cafe.implement.BusinessHourValidator;
import com.example.demo.member.domain.MemberId;
import com.example.demo.study.domain.Study;
import com.example.demo.study.domain.StudyContent;
import com.example.demo.study.domain.StudyId;
import com.example.demo.study.domain.StudyMemberId;
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
	private final BusinessHourReader businessHourReader;
	private final StudyReader studyReader;
	private final StudyEditor studyEditor;
	private final StudyMemberReader studyMemberReader;
	private final StudyMemberEditor studyMemberEditor;

	@Transactional
	public StudyId createStudy(MemberId memberId, LocalDateTime now, StudyContent content, CafeId cafeId) {
		validateStudyCreation(now, content.getSchedule().getStartDateTime());

		List<Study> participantStudies = studyReader.readUpcomingBy(memberId, now);
		studyValidator.validateStudyScheduleOverlap(content.getSchedule(), participantStudies);

		BusinessHour businessHour = businessHourReader.readBy(cafeId, content.getStartDate());
		businessHourValidator.validateBetweenBusinessHour(content.getSchedule(), businessHour);

		StudyId savedStudyId = studyEditor.saveWithCascade(content, cafeId, memberId);
		studyMemberEditor.save(memberId, savedStudyId, StudyRole.COORDINATOR);

		return savedStudyId;
	}

	public void deleteStudy(MemberId memberId, StudyId studyId, LocalDateTime now) {
		List<StudyMemberId> participantIds = studyMemberReader.readParticipantIdsBy(studyId);
		studyValidator.validateStudyMemberOnlyOne(participantIds);

		studyEditor.removeWithCascade(studyId, memberId, now);
	}

	private void validateStudyCreation(LocalDateTime now, LocalDateTime startDateTime) {
		studyValidator.validateStartDateTime(now, startDateTime);
		studyValidator.validateStartDate(startDateTime);
	}
}
