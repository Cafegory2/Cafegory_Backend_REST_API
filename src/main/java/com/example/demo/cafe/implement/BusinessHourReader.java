package com.example.demo.cafe.implement;

import java.time.DayOfWeek;

import com.example.demo.cafe.infrastructure.repository2.BusinessHourQueryRepository2;
import org.springframework.stereotype.Component;

import com.example.demo.cafe.domain.BusinessHour;
import com.example.demo.cafe.infrastructure.BusinessHourEntity;
import com.example.demo.cafe.infrastructure.BusinessHourQueryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BusinessHourReader {

	private final BusinessHourQueryRepository2 businessHourQueryRepository2;

	public BusinessHour readBy(Long cafeId, DayOfWeek startDate) {
		return businessHourQueryRepository2.findBy(cafeId, startDate);
	}
}
