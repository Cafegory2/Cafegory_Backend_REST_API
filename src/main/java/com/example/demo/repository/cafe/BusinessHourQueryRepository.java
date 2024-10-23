package com.example.demo.repository.cafe;

import static com.example.demo.implement.cafe.QBusinessHourEntity.businessHourEntity;

import java.time.DayOfWeek;

import org.springframework.stereotype.Repository;

import com.example.demo.implement.cafe.BusinessHourEntity;
import com.example.demo.implement.cafe.CafeEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BusinessHourQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;

	public BusinessHourEntity findWithCafeAndDayOfWeek(CafeEntity cafeEntity, DayOfWeek dayOfWeek) {
		return jpaQueryFactory.select(businessHourEntity)
			.from(businessHourEntity)
			.where(businessHourEntity.cafeEntity.eq(cafeEntity).and(businessHourEntity.dayOfWeek.eq(dayOfWeek)))
			.fetchOne();
	}
}
