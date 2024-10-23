package com.example.demo.helper;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.springframework.transaction.annotation.Transactional;

import com.example.demo.factory.TestBusinessHourFactory;
import com.example.demo.factory.TestCafeFactory;
import com.example.demo.implement.cafe.BusinessHourEntity;
import com.example.demo.implement.cafe.CafeEntity;
import com.example.demo.repository.cafe.BusinessHourRepository;
import com.example.demo.repository.cafe.CafeRepository;
import com.example.demo.util.TimeUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
public class CafeSaveHelper {

	private final CafeRepository cafeRepository;

	private final BusinessHourRepository businessHourRepository;

	private final TimeUtil timeUtil;

	public CafeEntity saveCafe() {
		CafeEntity cafe = TestCafeFactory.createCafe();
		return cafeRepository.save(cafe);
	}

	public CafeEntity saveCafeWith7daysFrom9To21() {
		CafeEntity cafe = TestCafeFactory.createCafe();
		makeBusinessHoursWith7daysFrom9To21(cafe);
		return cafeRepository.save(cafe);
	}

	private void makeBusinessHoursWith7daysFrom9To21(CafeEntity cafe) {
		for (DayOfWeek day : DayOfWeek.values()) {
			BusinessHourEntity businessHour = TestBusinessHourFactory.createBusinessHourWithDayAndTime(cafe, day,
				LocalTime.of(9, 0), LocalTime.of(21, 0));
			businessHourRepository.save(businessHour);
		}
	}

	public CafeEntity saveCafeWith24For7() {
		CafeEntity cafe = TestCafeFactory.createCafe();
		makeBusinessHourWith24For7(cafe);
		return cafeRepository.save(cafe);
	}

	private void makeBusinessHourWith24For7(CafeEntity cafe) {
		for (DayOfWeek day : DayOfWeek.values()) {
			BusinessHourEntity businessHour = TestBusinessHourFactory.createBusinessHourWithDayAnd24For7(cafe, day,
				timeUtil);
			businessHourRepository.save(businessHour);
		}
	}
}
