package com.example.demo.helper;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.implement.cafe.BusinessHour;
import com.example.demo.implement.cafe.Cafe;
import com.example.demo.factory.TestBusinessHourFactory;
import com.example.demo.factory.TestCafeFactory;
import com.example.demo.repository.cafe.BusinessHourRepository;
import com.example.demo.repository.cafe.CafeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Component
public class CafeSaveHelper {

	private final CafeRepository cafeRepository;

	private final BusinessHourRepository businessHourRepository;

	//	public Cafe saveCafe() {
	//		Cafe cafe = TestCafeFactory.createCafe();
	//		return cafeRepository.save(cafe);
	//	}
	//
	//	public Cafe saveCafeWith7daysFrom9To21() {
	//		Cafe cafe = TestCafeFactory.createCafe();
	//		List<BusinessHour> businessHours = createBusinessHoursWith7daysFrom9To21(cafe);
	//		cafe.changeBusinessHours(businessHours);
	//		return cafeRepository.save(cafe);
	//	}
	//
	//	private List<BusinessHour> createBusinessHoursWith7daysFrom9To21(Cafe cafe) {
	//		List<BusinessHour> businessHours = new ArrayList<>();
	//		List<String> daysOfWeek = generateDaysOfWeek();
	//		for (String day : daysOfWeek) {
	//			businessHours.add(
	//				createBusinessHourWithDayAndTime(cafe, day, LocalTime.of(9, 0), LocalTime.of(21, 0))
	//			);
	//		}
	//		return businessHours;
	//	}

	public Cafe saveCafeWith24For7() {
		Cafe cafe = TestCafeFactory.createCafe();
		makeBusinessHourWith24For7(cafe);
		return cafeRepository.save(cafe);
	}

	private void makeBusinessHourWith24For7(Cafe cafe) {
		for (DayOfWeek day : DayOfWeek.values()) {
			BusinessHour businessHour = TestBusinessHourFactory.createBusinessHourWithDayAnd24For7(cafe, day);
			businessHourRepository.save(businessHour);
		}
	}
}
