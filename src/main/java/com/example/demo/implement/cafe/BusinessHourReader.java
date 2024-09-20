package com.example.demo.implement.cafe;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import com.example.demo.repository.cafe.BusinessHourQueryDslRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BusinessHourReader {

	private final BusinessHourQueryDslRepository businessHourQueryDslRepository;

	public BusinessHour getBusinessHoursByCafeAndDay(Cafe cafe, DayOfWeek startDateTime) {
		return businessHourQueryDslRepository.findWithCafeAndDayOfWeek(cafe, startDateTime);
	}

}
