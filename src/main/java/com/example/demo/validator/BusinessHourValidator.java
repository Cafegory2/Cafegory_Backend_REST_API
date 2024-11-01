package com.example.demo.validator;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalTime;

import org.springframework.stereotype.Component;

import com.example.demo.cafe.domain.BusinessHour;
import com.example.demo.cafe.implement.BusinessHourOpenChecker;
import com.example.demo.exception.CafegoryException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BusinessHourValidator {

	private final BusinessHourOpenChecker openChecker;

	public void validateBetweenBusinessHour(LocalTime cafeStudyStartTime, LocalTime cafeStudyEndTime,
		BusinessHour businessHour) {
		boolean isBetweenBusinessHour = openChecker.checkBetweenBusinessHours(businessHour.getOpeningTme(),
			businessHour.getClosingTme(), cafeStudyStartTime, cafeStudyEndTime);
		if (!isBetweenBusinessHour) {
			throw new CafegoryException(STUDY_ONCE_CREATE_BETWEEN_CAFE_BUSINESS_HOURS);
		}
	}
}
