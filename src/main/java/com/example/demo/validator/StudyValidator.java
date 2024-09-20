package com.example.demo.validator;

import static com.example.demo.exception.ExceptionType.*;
import static com.example.demo.implement.study.CafeStudy.*;

import java.time.Duration;
import java.time.LocalDateTime;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.example.demo.exception.CafegoryException;
import com.example.demo.exception.ExceptionType;
import com.example.demo.util.TimeUtil;

import lombok.RequiredArgsConstructor;

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

	public void validateStudyCreation(String name, LocalDateTime now, LocalDateTime startDateTime, int maxParticipants) {
		validateNameLength(name);
		validateStartDateTime(now, startDateTime);
		validateStartDate(startDateTime);
		validateMaxParticipants(maxParticipants);
	}

	private void validateNameLength(String name) {
		if (name.length() > MAX_STUDY_NAME_LENGTH) {
			throw new CafegoryException(CAFE_STUDY_INVALID_NAME);
		}
	}

	private void validateStartDateTime(LocalDateTime now, LocalDateTime startDateTime) {
//		LocalDateTime now = timeUtil.now();
		Duration between = Duration.between(now, startDateTime);
		if (between.toSeconds() < MIN_DELAY_BEFORE_START) {
			log.info("between.toSeconds(): {}",between.toSeconds());
			throw new CafegoryException(STUDY_ONCE_WRONG_START_TIME);
		}
	}

	private void validateStartDate(LocalDateTime startDateTime) {
		LocalDateTime plusMonths = timeUtil.now().plusMonths(1);
		if (startDateTime.isAfter(plusMonths)) {
			throw new CafegoryException(CAFE_STUDY_WRONG_START_DATE);
		}
	}

	private void validateMaxParticipants(int maxParticipants) {
		if (maxParticipants > LIMIT_MEMBER_CAPACITY || maxParticipants < MIN_LIMIT_MEMBER_CAPACITY) {
			throw new CafegoryException(STUDY_ONCE_LIMIT_MEMBER_CAPACITY);
		}
	}
}
