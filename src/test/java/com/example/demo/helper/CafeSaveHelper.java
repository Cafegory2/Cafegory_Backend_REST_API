package com.example.demo.helper;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.domain.cafe.BusinessHour;
import com.example.demo.domain.cafe.Cafe;
import com.example.demo.factory.TestBusinessHourFactory;
import com.example.demo.factory.TestCafeFactory;
import com.example.demo.repository.cafe.CafeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CafeSaveHelper {

	private final CafeRepository cafeRepository;

	public Cafe saveDefaultCafe() {
		Cafe cafe = TestCafeFactory.createCafe();
		return cafeRepository.save(cafe);
	}

	public Cafe saveCafeWithBusinessHour(List<BusinessHour> businessHours) {
		Cafe cafe = TestCafeFactory.createCafeWithBusinessHours(businessHours);
		return cafeRepository.save(cafe);
	}

	public Cafe saveCafeWith24For7() {
		List<BusinessHour> businessHours = makeBusinessHourWith24For7();
		Cafe cafe = TestCafeFactory.createCafeWithBusinessHours(businessHours);
		return cafeRepository.save(cafe);
	}

	private List<BusinessHour> makeBusinessHourWith24For7() {
		List<String> daysOfWeek = List.of("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY");
		List<BusinessHour> businessHours = new ArrayList<>();
		for (String day : daysOfWeek) {
			businessHours.add(
				TestBusinessHourFactory.createBusinessHourWithDayAnd24For7(day)
			);
		}
		return businessHours;
	}
}
