package com.example.demo.repository.cafe;

import static com.example.demo.domain.cafe.QBusinessHour.*;

import java.time.DayOfWeek;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.cafe.BusinessHour;
import com.example.demo.domain.cafe.Cafe;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BusinessHourQueryDslRepository {
	private final JPAQueryFactory jpaQueryFactory;

	public BusinessHour findWithCafeAndDayOfWeek(Cafe cafe, DayOfWeek dayOfWeek) {
		return jpaQueryFactory.select(businessHour)
			.where(businessHour.cafe.eq(cafe).and(businessHour.dayOfWeek.eq(dayOfWeek)))
			.fetchOne();
	}
}
