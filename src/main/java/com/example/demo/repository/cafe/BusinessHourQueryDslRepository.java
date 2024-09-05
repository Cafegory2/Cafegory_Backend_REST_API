package com.example.demo.repository.cafe;

import static com.example.demo.implement.cafe.QBusinessHour.*;

import java.time.DayOfWeek;

import org.springframework.stereotype.Repository;

import com.example.demo.implement.cafe.BusinessHour;
import com.example.demo.implement.cafe.Cafe;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BusinessHourQueryDslRepository {
	private final JPAQueryFactory jpaQueryFactory;

	public BusinessHour findWithCafeAndDayOfWeek(Cafe cafe, DayOfWeek dayOfWeek) {
		return jpaQueryFactory.select(businessHour)
			.from(businessHour)
			.where(businessHour.cafe.eq(cafe).and(businessHour.dayOfWeek.eq(dayOfWeek)))
			.fetchOne();
	}
}
