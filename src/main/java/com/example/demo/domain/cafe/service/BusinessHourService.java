package com.example.demo.domain.cafe.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.cafe.domain.BusinessHour;
import com.example.demo.domain.cafe.domain.CafeId;
import com.example.demo.domain.cafe.implement.BusinessHourOpenChecker;
import com.example.demo.domain.cafe.implement.BusinessHourReader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BusinessHourService {

	private final BusinessHourReader businessHourReader;
	private final BusinessHourOpenChecker openChecker;

	public BusinessHour findBusinessHour(CafeId cafeId, LocalDateTime now) {
		return businessHourReader.readBy(cafeId, now.getDayOfWeek());
	}

	public boolean isOpen(BusinessHour businessHour, LocalDateTime now) {
		assert businessHour != null;
		return openChecker.checkByNowTime(businessHour.getDayOfWeek(), businessHour.getOpeningTme(),
			businessHour.getClosingTme(), now);
	}
}
