package com.example.demo.implement.cafe;

import java.time.DayOfWeek;

import com.example.demo.cafe.domain.Cafe;
import com.example.demo.cafe.infrastructure.BusinessHourEntity;
import org.springframework.stereotype.Component;

import com.example.demo.repository.cafe.BusinessHourQueryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BusinessHourReader {

	private final BusinessHourQueryRepository businessHourQueryRepository;

	public BusinessHourEntity readBy(Cafe cafe, DayOfWeek startDateTime) {
		return businessHourQueryRepository.findBy(cafe.getId(), startDateTime);
	}

}
