package com.example.demo.repository.cafe;

import static com.example.demo.domain.QCafeImpl.*;
import static io.hypersistence.utils.hibernate.util.StringUtils.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.CafeImpl;
import com.example.demo.domain.MaxAllowableStay;
import com.example.demo.service.dto.CafeSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class CafeQueryRepository {

	private final EntityManager em;
	private final JPAQueryFactory queryFactory;

	@Autowired
	public CafeQueryRepository(EntityManager em) {
		this.em = em;
		this.queryFactory = new JPAQueryFactory(em);
	}

	public List<CafeImpl> findWithDynamicFilterAndNoPaging(CafeSearchCondition searchCondition) {
		return queryFactory
			.selectFrom(cafeImpl)
			// .join(cafeImpl.businessHours, businessHour)
			// .join(cafeImpl.snsDetails, snsDetail)
			// .join(cafeImpl.reviews, reviewImpl)
			// .join(cafeImpl.menus, menu)
			.where(
				isAbleToStudy(searchCondition.isAbleToStudy()),
				regionContains(searchCondition.getRegion()),
				maxAllowableStayInLoe(searchCondition.getMaxAllowableStay())
				// minBeveragePriceInLoe(searchCondition.getMinMenuPrice())
			)
			.fetch();

	}

	public Page<CafeImpl> findWithDynamicFilter(CafeSearchCondition searchCondition, Pageable pageable) {

		List<CafeImpl> content = queryFactory
			.selectFrom(cafeImpl)
			.where(
				isAbleToStudy(searchCondition.isAbleToStudy()),
				regionContains(searchCondition.getRegion()),
				maxAllowableStayInLoe(searchCondition.getMaxAllowableStay())
				// minBeveragePriceInLoe(searchCondition.getMinMenuPrice())
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = queryFactory
			.select(cafeImpl.count())
			.from(cafeImpl)
			.where(
				isAbleToStudy(searchCondition.isAbleToStudy()),
				regionContains(searchCondition.getRegion()),
				maxAllowableStayInLoe(searchCondition.getMaxAllowableStay())
				// minBeveragePriceInLoe(searchCondition.getMinMenuPrice(), )
			);
		return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
	}

	// private BooleanExpression minBeveragePriceInLoe(MinMenuPrice minMenuPrice) {
	// 	return minMenuPrice == null
	// 		? null : cafeImpl.minBeveragePrice.in(MinMenuPrice.findLoe(minMenuPrice));
	// }

	//매개변수인 MaxAllowableStay보다 작거나 같은 MaxAllowableStay의 Enum상수가 in절안에 List로 들어감
	private BooleanExpression maxAllowableStayInLoe(MaxAllowableStay maxTime) {
		return maxTime == null
			? null : cafeImpl.maxAllowableStay.in(MaxAllowableStay.findLoe(maxTime));
	}

	private BooleanExpression regionContains(String region) {
		return isBlank(region) ? null : cafeImpl.address.region.contains(region);
	}

	private BooleanExpression isAbleToStudy(boolean isAbleToStudy) {
		return cafeImpl.isAbleToStudy.eq(isAbleToStudy);
	}
}
