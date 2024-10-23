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
		CafeEntity cafeEntity = TestCafeFactory.createCafe();
		return cafeRepository.save(cafeEntity);
	}

	public CafeEntity saveCafeWith7daysFrom9To21() {
		CafeEntity cafeEntity = TestCafeFactory.createCafe();
		makeBusinessHoursWith7daysFrom9To21(cafeEntity);
		return cafeRepository.save(cafeEntity);
	}

	private void makeBusinessHoursWith7daysFrom9To21(CafeEntity cafeEntity) {
		for (DayOfWeek day : DayOfWeek.values()) {
			BusinessHourEntity businessHourEntity = TestBusinessHourFactory.createBusinessHourWithDayAndTime(cafeEntity, day,
				LocalTime.of(9, 0), LocalTime.of(21, 0));
			businessHourRepository.save(businessHourEntity);
		}
	}

	public CafeEntity saveCafeWith24For7() {
		CafeEntity cafeEntity = TestCafeFactory.createCafe();
		makeBusinessHourWith24For7(cafeEntity);
		return cafeRepository.save(cafeEntity);
	}

	private void makeBusinessHourWith24For7(CafeEntity cafeEntity) {
		for (DayOfWeek day : DayOfWeek.values()) {
			BusinessHourEntity businessHourEntity = TestBusinessHourFactory.createBusinessHourWithDayAnd24For7(cafeEntity, day,
				timeUtil);
			businessHourRepository.save(businessHourEntity);
		}
	}
}
