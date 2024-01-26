package com.example.demo.repository.cafe;

import static com.example.demo.domain.QCafeImpl.*;
import static io.hypersistence.utils.hibernate.util.StringUtils.*;

import java.util.List;

import javax.persistence.EntityManager;

import com.example.demo.domain.CafeImpl;
import com.example.demo.domain.MaxAllowableStay;
import com.example.demo.service.dto.CafeSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class CafeRepositoryCustomImpl implements CafeRepositoryCustom {

	private final EntityManager em;
	private final JPAQueryFactory queryFactory;

	public CafeRepositoryCustomImpl(EntityManager em) {
		this.em = em;
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public List<CafeImpl> findWithDynamicFilter(CafeSearchCondition searchCondition) {
		List<CafeImpl> cafes = queryFactory
			.selectFrom(cafeImpl)
			// .join(cafeImpl.businessHours, businessHour)
			// .join(cafeImpl.snsDetails, snsDetail)
			// .join(cafeImpl.reviews, reviewImpl)
			// .join(cafeImpl.menus, menu)
			.where(
				isAbleToStudy(searchCondition.isAbleToStudy()),
				regionContains(searchCondition.getRegion()),
				maxAllowableStayInLoe(searchCondition.getMaxAllowableStay())
			)
			.fetch();

		return cafes;

	}

	//매개변수인 MaxAllowableStay보다 작거나 같은 MaxAllowableStay의 Enum상수가 in절안에 List로 들어감
	private BooleanExpression maxAllowableStayInLoe(MaxAllowableStay maxTime) {
		return maxTime == null
			? null : cafeImpl.maxAllowableStay.in(MaxAllowableStay.findByLoeCondition(maxTime));
	}

	private BooleanExpression regionContains(String region) {
		return isBlank(region) ? null : cafeImpl.address.region.contains(region);
	}

	private BooleanExpression isAbleToStudy(boolean isAbleToStudy) {
		return cafeImpl.isAbleToStudy.eq(isAbleToStudy);
	}
}
