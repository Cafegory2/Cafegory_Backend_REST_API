package com.example.demo.cafe.implement;

import java.time.DayOfWeek;

import com.example.demo.cafe.domain.CafeId;
import com.example.demo.cafe.infrastructure.repository2.BusinessHourQueryRepository2;
import com.example.demo.exception.CafegoryException;
import com.example.demo.exception.ExceptionType;
import org.springframework.stereotype.Component;

import com.example.demo.cafe.domain.BusinessHour;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BusinessHourReader {

	private final BusinessHourQueryRepository2 businessHourQueryRepository2;

	public BusinessHour readBy(CafeId cafeId, DayOfWeek startDate) {
		return businessHourQueryRepository2.findBy(cafeId, startDate)
				.orElseThrow(() -> new CafegoryException(ExceptionType.CAFE_BUSINESS_HOUR_NOT_FOUND));
	}
}
