package com.example.demo.validator;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalTime;

import org.springframework.stereotype.Component;

import com.example.demo.exception.CafegoryException;
import com.example.demo.implement.cafe.BusinessHourEntity;
import com.example.demo.implement.cafe.BusinessHourOpenChecker;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BusinessHourValidator {

	private final BusinessHourOpenChecker openChecker;

	public void validateBetweenBusinessHour(LocalTime cafeStudyStartTime, LocalTime cafeStudyEndTime,
		BusinessHourEntity businessHourEntity) {
		boolean isBetweenBusinessHour = openChecker.checkBetweenBusinessHours(businessHourEntity.getOpeningTime(),
			businessHourEntity.getClosingTime(), cafeStudyStartTime, cafeStudyEndTime);
		if (!isBetweenBusinessHour) {
			throw new CafegoryException(STUDY_ONCE_CREATE_BETWEEN_CAFE_BUSINESS_HOURS);
		}
	}
}
