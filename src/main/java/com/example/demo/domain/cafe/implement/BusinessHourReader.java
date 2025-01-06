package com.example.demo.domain.cafe.implement;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import com.example.demo.domain.cafe.domain.BusinessHour;
import com.example.demo.domain.cafe.domain.CafeId;
import com.example.demo.domain.cafe.repository.BusinessHourQueryRepository;
import com.example.demo.domain.exception.CafegoryException;
import com.example.demo.domain.exception.ExceptionType;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BusinessHourReader {

	private final BusinessHourQueryRepository businessHourQueryRepository;

	public BusinessHour readBy(CafeId cafeId, DayOfWeek startDate) {
		return businessHourQueryRepository.findBy(cafeId, startDate)
			.orElseThrow(() -> new CafegoryException(ExceptionType.CAFE_BUSINESS_HOUR_NOT_FOUND));
	}
}
