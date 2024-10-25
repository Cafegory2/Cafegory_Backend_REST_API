package com.example.demo.implement.cafe;

import java.time.DayOfWeek;

import com.example.demo.cafe.infrastructure.BusinessHourEntity;
import com.example.demo.cafe.infrastructure.CafeEntity;
import org.springframework.stereotype.Component;

import com.example.demo.repository.cafe.BusinessHourQueryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BusinessHourReader {

	private final BusinessHourQueryRepository businessHourQueryRepository;

	public BusinessHourEntity getBusinessHoursByCafeAndDay(CafeEntity cafe, DayOfWeek startDateTime) {
		return businessHourQueryRepository.findWithCafeAndDayOfWeek(cafe, startDateTime);
	}

}
