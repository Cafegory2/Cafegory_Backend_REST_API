package com.example.demo.cafe.implement;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import com.example.demo.cafe.domain.BusinessHour;
import com.example.demo.cafe.infrastructure.BusinessHourEntity;
import com.example.demo.cafe.infrastructure.BusinessHourQueryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BusinessHourReader {

	private final BusinessHourQueryRepository businessHourQueryRepository;

	public BusinessHour readBy(Long cafeId, DayOfWeek startDate) {
		BusinessHourEntity businessHourEntity = businessHourQueryRepository.findBy(cafeId, startDate);

		return businessHourEntity.toBusinessHour();
	}

}
