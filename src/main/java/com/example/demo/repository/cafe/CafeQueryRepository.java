package com.example.demo.repository.cafe;

import static com.example.demo.domain.QBusinessHour.*;
import static com.example.demo.domain.QCafeImpl.*;
import static io.hypersistence.utils.hibernate.util.StringUtils.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.CafeImpl;
import com.example.demo.domain.MaxAllowableStay;
import com.example.demo.domain.MinMenuPrice;
import com.example.demo.dto.CafeSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
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
			.where(
				isAbleToStudy(searchCondition.isAbleToStudy()),
				regionContains(searchCondition.getRegion()),
				maxAllowableStayInLoe(searchCondition.getMaxAllowableStay()),
				minBeveragePriceLoe(searchCondition.getMinMenuPrice()),
				businessHourBetween(searchCondition.getStartTime(), searchCondition.getEndTime(),
					searchCondition.getNow())
			)
			.fetch();

	}

	public Page<CafeImpl> findWithDynamicFilter(CafeSearchCondition searchCondition, Pageable pageable) {

		List<CafeImpl> content = queryFactory
			.selectFrom(cafeImpl)
			.where(
				isAbleToStudy(searchCondition.isAbleToStudy()),
				regionContains(searchCondition.getRegion()),
				maxAllowableStayInLoe(searchCondition.getMaxAllowableStay()),
				minBeveragePriceLoe(searchCondition.getMinMenuPrice())
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
				maxAllowableStayInLoe(searchCondition.getMaxAllowableStay()),
				minBeveragePriceLoe(searchCondition.getMinMenuPrice())
			);
		System.out.println(content);
		return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
	}

	private BooleanExpression businessHourBetween(LocalTime filteringStartTime, LocalTime filteringEndTime,
		LocalDateTime now) {
		if (filteringStartTime == null || filteringEndTime == null) {
			return null;
		}
		String nowDayOfWeek = now.getDayOfWeek().toString();
		// String nowDayOfWeek = LocalDateTime.now().getDayOfWeek().toString();
		return cafeImpl.id.in(
			JPAExpressions
				.select(businessHour.cafe.id)
				.from(businessHour)
				.where(
					businessHour.day.eq(nowDayOfWeek),
					businessHour.startTime.eq(filteringStartTime).or(businessHour.startTime.after(filteringStartTime)),
					businessHour.endTime.eq(filteringEndTime).or(businessHour.endTime.before(filteringEndTime))
				)
		);
	}

	private BooleanExpression minBeveragePriceLoe(MinMenuPrice minMenuPrice) {
		return minMenuPrice == null ? null : cafeImpl.minBeveragePrice.loe(minMenuPrice.getRealValue());
	}

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
