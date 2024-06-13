package com.example.demo.helper.save;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.example.demo.builder.TestBusinessHourBuilder;
import com.example.demo.builder.TestCafeBuilder;
import com.example.demo.domain.cafe.BusinessHour;
import com.example.demo.domain.cafe.Cafe;

public class CafeSaveHelper {

	@PersistenceContext
	private EntityManager em;

	public Cafe persistDefaultCafe() {
		Cafe cafe = new TestCafeBuilder().build();
		em.persist(cafe);
		return cafe;
	}

	public Cafe persistCafeWithImpossibleStudy() {
		Cafe cafe = new TestCafeBuilder().isNotAbleToStudy().build();
		em.persist(cafe);
		return cafe;
	}

	public Cafe persistCafeWithBusinessHour(List<BusinessHour> businessHours) {
		Cafe cafe = new TestCafeBuilder().businessHours(businessHours).build();
		em.persist(cafe);
		return cafe;
	}

	public Cafe persistCafeWith24For7() {
		List<BusinessHour> businessHours = makeBusinessHourWith24For7();
		Cafe cafe = new TestCafeBuilder().businessHours(businessHours).build();
		em.persist(cafe);
		return cafe;
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
