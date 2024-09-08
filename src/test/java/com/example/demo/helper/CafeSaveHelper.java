package com.example.demo.helper;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.factory.TestBusinessHourFactory;
import com.example.demo.factory.TestCafeFactory;
import com.example.demo.implement.cafe.BusinessHour;
import com.example.demo.implement.cafe.Cafe;
import com.example.demo.repository.cafe.BusinessHourRepository;
import com.example.demo.repository.cafe.CafeRepository;
import com.example.demo.util.TruncatedTimeUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Component
public class CafeSaveHelper {

	private final CafeRepository cafeRepository;

	private final BusinessHourRepository businessHourRepository;

	private final TruncatedTimeUtil truncatedTimeUtil;

	//	public Cafe saveCafe() {
	//		Cafe cafe = TestCafeFactory.createCafe();
	//		return cafeRepository.save(cafe);
	//	}

	public Cafe saveCafeWith7daysFrom9To21() {
		Cafe cafe = TestCafeFactory.createCafe();
		makeBusinessHoursWith7daysFrom9To21(cafe);
		return cafeRepository.save(cafe);
	}

	private void makeBusinessHoursWith7daysFrom9To21(Cafe cafe) {
		for (DayOfWeek day : DayOfWeek.values()) {
			BusinessHour businessHour = TestBusinessHourFactory.createBusinessHourWithDayAndTime(cafe, day,
				LocalTime.of(9, 0), LocalTime.of(21, 0));
			businessHourRepository.save(businessHour);
		}
	}

	public Cafe saveCafeWith24For7() {
		Cafe cafe = TestCafeFactory.createCafe();
		makeBusinessHourWith24For7(cafe);
		return cafeRepository.save(cafe);
	}

	private void makeBusinessHourWith24For7(Cafe cafe) {
		for (DayOfWeek day : DayOfWeek.values()) {
			BusinessHour businessHour = TestBusinessHourFactory.createBusinessHourWithDayAnd24For7(cafe, day,
				truncatedTimeUtil);
			businessHourRepository.save(businessHour);
		}
	}
}
