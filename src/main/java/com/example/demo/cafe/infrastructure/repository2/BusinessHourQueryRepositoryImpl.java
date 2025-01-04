package com.example.demo.cafe.infrastructure.repository2;

import java.time.DayOfWeek;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.cafe.domain.BusinessHour;
import com.example.demo.cafe.domain.CafeId;
import com.example.demo.cafe.infrastructure.BusinessHourEntity;
import com.example.demo.cafe.infrastructure.QBusinessHourEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BusinessHourQueryRepositoryImpl implements BusinessHourQueryRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Optional<BusinessHour> findBy(CafeId cafeId, DayOfWeek dayOfWeek) {
		BusinessHourEntity businessHourEntity =
			jpaQueryFactory.select(QBusinessHourEntity.businessHourEntity)
				.from(QBusinessHourEntity.businessHourEntity)
				.where(QBusinessHourEntity.businessHourEntity.cafe.id.eq(cafeId.getId())
					.and(QBusinessHourEntity.businessHourEntity.dayOfWeek.eq(dayOfWeek)))
				.fetchOne();

		return Optional.ofNullable(businessHourEntity).map(BusinessHourEntity::toBusinessHour);
	}
}
