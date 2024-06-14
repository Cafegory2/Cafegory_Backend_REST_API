package com.example.demo.helper;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.builder.TestBusinessHourBuilder;
import com.example.demo.builder.TestCafeBuilder;
import com.example.demo.domain.cafe.BusinessHour;
import com.example.demo.domain.cafe.Cafe;
import com.example.demo.repository.cafe.CafeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CafeSaveHelper {

	private final CafeRepository cafeRepository;

	public Cafe persistDefaultCafe() {
		Cafe cafe = new TestCafeBuilder().build();
		return cafeRepository.save(cafe);
	}

	public Cafe persistCafeWithBusinessHour(List<BusinessHour> businessHours) {
		Cafe cafe = new TestCafeBuilder().businessHours(businessHours).build();
		return cafeRepository.save(cafe);
	}

	public Cafe persistCafeWith24For7() {
		List<BusinessHour> businessHours = makeBusinessHourWith24For7();
		Cafe cafe = new TestCafeBuilder().businessHours(businessHours).build();
		return cafeRepository.save(cafe);
	}

	private List<BusinessHour> makeBusinessHourWith24For7() {
		List<String> daysOfWeek = List.of("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY");
		List<BusinessHour> businessHours = new ArrayList<>();
		for (String day : daysOfWeek) {
			businessHours.add(
				new TestBusinessHourBuilder()
					.day(day)
					.build()
			);
		}
		return businessHours;
	}
}
