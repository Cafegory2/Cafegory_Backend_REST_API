package com.example.demo.repository.cafe;


import java.time.DayOfWeek;

import org.springframework.stereotype.Repository;

import com.example.demo.cafe.infrastructure.BusinessHourEntity;
import com.example.demo.cafe.infrastructure.CafeEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import static com.example.demo.cafe.infrastructure.QBusinessHourEntity.businessHourEntity;

@Repository
@RequiredArgsConstructor
public class BusinessHourQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;

	public BusinessHourEntity findWithCafeAndDayOfWeek(CafeEntity cafeEntity, DayOfWeek dayOfWeek) {
		return jpaQueryFactory.select(businessHourEntity)
			.from(businessHourEntity)
			.where(businessHourEntity.cafe.eq(cafeEntity).and(businessHourEntity.dayOfWeek.eq(dayOfWeek)))
			.fetchOne();
	}
}
