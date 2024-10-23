package com.example.demo.implement.cafe;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import com.example.demo.repository.cafe.BusinessHourQueryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BusinessHourReader {

	private final BusinessHourQueryRepository businessHourQueryRepository;

	public BusinessHourEntity getBusinessHoursByCafeAndDay(CafeEntity cafeEntity, DayOfWeek startDateTime) {
		return businessHourQueryRepository.findWithCafeAndDayOfWeek(cafeEntity, startDateTime);
	}

}
