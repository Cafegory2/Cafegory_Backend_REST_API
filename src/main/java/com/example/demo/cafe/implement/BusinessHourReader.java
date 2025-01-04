package com.example.demo.cafe.implement;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import com.example.demo.cafe.domain.BusinessHour;
import com.example.demo.cafe.domain.CafeId;
import com.example.demo.cafe.infrastructure.repository2.BusinessHourQueryRepository;
import com.example.demo.exception.CafegoryException;
import com.example.demo.exception.ExceptionType;

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
