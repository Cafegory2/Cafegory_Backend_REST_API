package com.example.demo.cafe.infrastructure;

import static com.example.demo.cafe.infrastructure.QBusinessHourEntity.*;
import static com.example.demo.cafe.infrastructure.QCafeEntity.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.demo.cafe.domain.CafeTagType;
import com.example.demo.cafe.presentation.CafeSearchListRequest;
import com.example.demo.exception.CafegoryException;
import com.example.demo.exception.ExceptionType;
import com.example.demo.trash.dto.SliceResponse;
import com.example.demo.util.PagingUtil;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CafeQueryRepository {

	private final JPAQueryFactory queryFactory;

	public SliceResponse<CafeEntity> findCafeByRegionAndKeyword(CafeSearchListRequest request) {
		Pageable pageable = PagingUtil.of(request.getPage(), request.getSizePerPage());

		JPAQuery<Long> cafeIds = queryFactory
			.select(businessHourEntity.cafe.id).distinct()
			.from(businessHourEntity)
			.join(businessHourEntity.cafe, cafeEntity)
			.where(
				businessHourContains(request.getOpeningDateTime(), request.getClosingDateTime()),
				keywordContains(request.getKeyword())
					.or(cafeNameContains(request.getKeyword())),
				hasAllCafeTagTypes(request.getCafeTagTypes())
			)
			.orderBy(cafeEntity.cafeStudies.size().asc());

		JPAQuery<CafeEntity> query = queryFactory
			.select(cafeEntity)
			.from(cafeEntity)
			.where(cafeEntity.id.in(cafeIds));

		return SliceResponse.of(PagingUtil.toSlice(query, pageable));
	}

	private BooleanExpression keywordContains(String keyword) {
		// 만약 이 메서드가 동작하지 않는다면 DB의 맞는 Expressions.stringTemplate 의 내부 구문을 바꿔야 한다.
		// DB에 등록된 키워드와 파라미터의 키워드 둘다 공백제거 한뒤 비교한다.
		return keyword == null ? null :
			Expressions.stringTemplate("function('replace', {0}, ' ', '')", cafeEntity.cafeKeywords.any().keyword)
				.likeIgnoreCase("%" + keyword.replace(" ", "") + "%");
	}

	private BooleanExpression cafeNameContains(String cafeName) {
		// 만약 이 메서드가 동작하지 않는다면 DB의 맞는 Expressions.stringTemplate 의 내부 구문을 바꿔야 한다.
		// DB에 등록된 키워드와 파라미터의 키워드 둘다 공백제거 한뒤 비교한다.
		return cafeName == null ? null :
			Expressions.stringTemplate("function('replace', {0}, ' ', '')", cafeEntity.name)
				.likeIgnoreCase("%" + cafeName.replace(" ", "") + "%");
	}

	private BooleanExpression businessHourContains(LocalDateTime openingDateTime, LocalDateTime closingDateTime) {
		boolean isStartDateAndEndDateEquals = openingDateTime.getDayOfWeek().equals(closingDateTime.getDayOfWeek());

		// 시간 유효성 검사
		if (openingDateTime.isAfter(closingDateTime) || openingDateTime.isEqual(closingDateTime)) {
			throw new CafegoryException(ExceptionType.CAFE_SEARCH_INVALID_TIME_RANGE);
		}

		// 시작시간과 종료시간이 같은 날인 경우
		if (isStartDateAndEndDateEquals) {
			return businessHourEntity.dayOfWeek.eq(openingDateTime.getDayOfWeek())
				.and(businessHourEntity.openingTime.loe(closingDateTime.toLocalTime())
					.and(businessHourEntity.closingTime.goe(openingDateTime.toLocalTime())));
		}

		// 시작시간과 종료시간이 다른 날인 경우
		return businessHourEntity.dayOfWeek.eq(closingDateTime.getDayOfWeek())
			.and(businessHourEntity.openingTime.loe(closingDateTime.toLocalTime()))
			.or((businessHourEntity.dayOfWeek.eq(openingDateTime.getDayOfWeek()))
				.and(businessHourEntity.closingTime.goe(openingDateTime.toLocalTime())));
	}

	private BooleanExpression hasAllCafeTagTypes(List<CafeTagType> cafeTagTypes) {
		if (cafeTagTypes == null || cafeTagTypes.isEmpty())
			return null;

		return cafeTagTypes.stream()
			.map(type -> cafeEntity.cafeCafeTags.any().cafeTag.type.eq(type))
			.reduce(BooleanExpression::and)
			.orElse(null);
	}

	// public List<Cafe> findWithDynamicFilterAndNoPaging(CafeSearchCondition searchCondition) {
	// 	return queryFactory
	// 		.selectFrom(cafe)
	// 		.where(
	// 			isAbleToStudy(searchCondition.isAbleToStudy()),
	// 			regionContains(searchCondition.getRegion()),
	// 			maxAllowableStayInLoe(searchCondition.getMaxAllowableStay()),
	// 			minBeveragePriceLoe(searchCondition.getMinMenuPrice()),
	// 			businessHourBetween(searchCondition.getStartTime(), searchCondition.getEndTime(),
	// 				searchCondition.getNow())
	// 		)
	// 		.fetch();
	// }
	//
	// public Page<Cafe> findWithDynamicFilter(CafeSearchCondition searchCondition, Pageable pageable) {
	// 	List<Cafe> content = queryFactory
	// 		.selectFrom(cafe)
	// 		.where(
	// 			isAbleToStudy(searchCondition.isAbleToStudy()),
	// 			regionContains(searchCondition.getRegion()),
	// 			maxAllowableStayInLoe(searchCondition.getMaxAllowableStay()),
	// 			minBeveragePriceLoe(searchCondition.getMinMenuPrice()),
	// 			businessHourBetween(searchCondition.getStartTime(), searchCondition.getEndTime(),
	// 				searchCondition.getNow())
	// 		)
	// 		.offset(pageable.getOffset())
	// 		.limit(pageable.getPageSize())
	// 		.fetch();
	//
	// 	JPAQuery<Long> countQuery = queryFactory
	// 		.select(cafe.count())
	// 		.from(cafe)
	// 		.where(
	// 			isAbleToStudy(searchCondition.isAbleToStudy()),
	// 			regionContains(searchCondition.getRegion()),
	// 			maxAllowableStayInLoe(searchCondition.getMaxAllowableStay()),
	// 			minBeveragePriceLoe(searchCondition.getMinMenuPrice()),
	// 			businessHourBetween(searchCondition.getStartTime(), searchCondition.getEndTime(),
	// 				searchCondition.getNow())
	// 		);
	// 	return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
	// }
	//
	// private BooleanExpression businessHourBetween(LocalTime filteringStartTime, LocalTime filteringEndTime,
	// 	LocalDateTime now) {
	// 	if (filteringStartTime == null || filteringEndTime == null || now == null) {
	// 		return null;
	// 	}
	// 	String nowDayOfWeek = now.getDayOfWeek().toString();
	// 	return cafe.id.in(
	// 		JPAExpressions
	// 			.select(businessHour.cafe.id)
	// 			.from(businessHour)
	// 			.where(
	// 				businessHour.day.eq(nowDayOfWeek),
	// 				businessHour.startTime.eq(filteringStartTime).or(businessHour.startTime.after(filteringStartTime)),
	// 				businessHour.endTime.eq(filteringEndTime).or(businessHour.endTime.before(filteringEndTime))
	// 			)
	// 	);
	// }
	//
	// private BooleanExpression minBeveragePriceLoe(MinMenuPrice minMenuPrice) {
	// 	return minMenuPrice == null ? null : cafe.minBeveragePrice.loe(minMenuPrice.getRealValue());
	// }
	//
	// //매개변수인 MaxAllowableStay보다 작거나 같은 MaxAllowableStay의 Enum상수가 in절안에 List로 들어감
	// private BooleanExpression maxAllowableStayInLoe(MaxAllowableStay maxTime) {
	// 	return maxTime == null
	// 		? null : cafe.maxAllowableStay.in(MaxAllowableStay.findLoe(maxTime));
	// }
	//
	// private BooleanExpression regionContains(String region) {
	// 	return isBlank(region) ? null : cafe.address.region.contains(region);
	// }
	//
	// private BooleanExpression isAbleToStudy(boolean isAbleToStudy) {
	// 	return cafe.isAbleToStudy.eq(isAbleToStudy);
	// }
}
