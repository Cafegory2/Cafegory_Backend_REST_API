package com.example.demo.db.cafe.businessHour;

import java.time.DayOfWeek;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.cafe.domain.BusinessHour;
import com.example.demo.domain.cafe.domain.CafeId;
import com.example.demo.domain.cafe.repository.BusinessHourQueryRepository;
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
