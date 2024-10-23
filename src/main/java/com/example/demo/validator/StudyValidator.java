package com.example.demo.validator;

import static com.example.demo.exception.ExceptionType.*;
import static com.example.demo.implement.study.CafeStudyEntity.*;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.demo.exception.CafegoryException;
import com.example.demo.exception.ExceptionType;
import com.example.demo.implement.member.MemberEntity;
import com.example.demo.implement.study.CafeStudyEntity;
import com.example.demo.util.TimeUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class StudyValidator {

	private static final int MAX_STUDY_NAME_LENGTH = 20;

	private final TimeUtil timeUtil;

	public void validateEmptyOrWhiteSpace(String target, ExceptionType exceptionType) {
		if (target.isBlank()) {
			throw new CafegoryException(exceptionType);
		}
	}

	public void validateNameLength(String name) {
		if (name.length() > MAX_STUDY_NAME_LENGTH) {
			throw new CafegoryException(CAFE_STUDY_INVALID_NAME);
		}
	}

	public void validateStartDateTime(LocalDateTime now, LocalDateTime startDateTime) {
		LocalDateTime nowPlusHour = now.plusSeconds(MIN_DELAY_BEFORE_START - 1);

		if (!startDateTime.isAfter(nowPlusHour)) {
			throw new CafegoryException(STUDY_ONCE_WRONG_START_TIME);
		}
	}

	public void validateStartDate(LocalDateTime startDateTime) {
		LocalDateTime plusMonths = timeUtil.now().plusMonths(1);
		if (startDateTime.isAfter(plusMonths)) {
			throw new CafegoryException(CAFE_STUDY_WRONG_START_DATE);
		}
	}

	public void validateMaxParticipants(int maxParticipants) {
		if (maxParticipants > LIMIT_MEMBER_CAPACITY || maxParticipants < MIN_LIMIT_MEMBER_CAPACITY) {
			throw new CafegoryException(STUDY_ONCE_LIMIT_MEMBER_CAPACITY);
		}
	}

	public void validateMemberIsCafeStudyCoordinator(MemberEntity memberId, CafeStudyEntity cafeStudy) {
		if (!cafeStudy.getCoordinator().equals(memberId)) {
			throw new CafegoryException(CAFE_STUDY_INVALID_LEADER);
		}
	}

	public void validateCafeStudyMembersPresent(CafeStudyEntity cafeStudy) {
		if (!cafeStudy.getCafeStudyMembers().isEmpty()) {
			throw new CafegoryException(CAFE_STUDY_DELETE_FAIL_MEMBERS_PRESENT);
		}
	}
}
