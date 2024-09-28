package com.example.demo.repository.study;

import com.example.demo.implement.study.CafeStudy;
import com.example.demo.implement.study.CafeStudyTagType;
import com.example.demo.implement.study.CafeTagType;
import com.example.demo.implement.study.QCafeStudyCafeStudyTag;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.example.demo.implement.cafe.QCafe.*;
import static com.example.demo.implement.study.QCafeStudy.*;
import static com.example.demo.implement.study.QCafeStudyCafeStudyTag.*;
import static com.example.demo.implement.study.QCafeStudyTag.cafeStudyTag;

@Repository
@RequiredArgsConstructor
public class CafeStudyQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<CafeStudy> findCafeStudies(String keyword, LocalDate date, CafeStudyTagType cafeStudyTagType, CafeTagType... cafeTagType) {
        return queryFactory
            .select(cafeStudy).distinct()
            .from(cafeStudy)
            .join(cafeStudy.cafe, cafe).fetchJoin()
            .where(
                keywordContains(keyword)
                    .or(cafeStudyNameContains(keyword)),
                dateEq(date),
                cafeStudyTagTypeEq(cafeStudyTagType),
                hasAllCafeTagTypes(cafeTagType)
            )
            .fetch();
    }

//    private BooleanExpression cafeTagTypeEq(CafeTagType... cafeTagType) {
//        return cafeTagType == null ? null : cafe.cafeCafeTags.any().cafeTag.type.eq(cafeTagType);
//    }

    private BooleanExpression hasAllCafeTagTypes(CafeTagType... cafeTagTypes) {
        if(cafeTagTypes == null || cafeTagTypes.length == 0) return null;

        return Arrays.stream(cafeTagTypes)
            .map(type -> cafe.cafeCafeTags.any().cafeTag.type.eq(type))
            .reduce(BooleanExpression::and)
            .orElse(null);
    }

    private BooleanExpression cafeStudyTagTypeEq(CafeStudyTagType cafeStudyTagType) {
        return cafeStudyTagType == null ? null : cafeStudy.cafeStudyCafeStudyTags.any().cafeStudyTag.type.eq(cafeStudyTagType);
    }

    private BooleanExpression dateEq(LocalDate date) {
        if(date == null) return null;

        return cafeStudy.studyPeriod.startDateTime.year().eq(date.getYear())
            .and(cafeStudy.studyPeriod.startDateTime.month().eq(date.getMonthValue()))
            .and(cafeStudy.studyPeriod.startDateTime.dayOfMonth().eq(date.getDayOfMonth()));
    }

    private BooleanExpression keywordContains(String keyword) {
        // 만약 이 메서드가 동작하지 않는다면 DB의 맞는 Expressions.stringTemplate 의 내부 구문을 바꿔야 한다.
        // DB에 등록된 키워드와 파라미터의 키워드 둘다 공백제거 한뒤 비교한다.
        return keyword == null ? null : Expressions.stringTemplate("function('replace', {0}, ' ', '')", cafe.cafeKeywords.any().keyword)
            .likeIgnoreCase("%" + keyword.replace(" ", "") + "%");
    }

    private BooleanExpression cafeStudyNameContains(String cafeStudyName) {
        // 만약 이 메서드가 동작하지 않는다면 DB의 맞는 Expressions.stringTemplate 의 내부 구문을 바꿔야 한다.
        // DB에 등록된 키워드와 파라미터의 키워드 둘다 공백제거 한뒤 비교한다.
        return cafeStudyName == null ? null : Expressions.stringTemplate("function('replace', {0}, ' ', '')", cafeStudy.name)
            .likeIgnoreCase("%" + cafeStudyName.replace(" ", "") + "%");
    }



//    private BooleanExpression keywordContains(String keyword) {
//        return keyword == null ? null : cafe.cafeKeywords.any().keyword.likeIgnoreCase("%" + keyword + "%");
//    }

//    public Page<Cafe> findWithDynamicFilter(CafeSearchCondition searchCondition, Pageable pageable) {
// 		List<Cafe> content = queryFactory
// 			.selectFrom(cafe)
// 			.where(
// 				isAbleToStudy(searchCondition.isAbleToStudy()),
// 				regionContains(searchCondition.getRegion()),
// 				maxAllowableStayInLoe(searchCondition.getMaxAllowableStay()),
// 				minBeveragePriceLoe(searchCondition.getMinMenuPrice()),
// 				businessHourBetween(searchCondition.getStartTime(), searchCondition.getEndTime(),
// 					searchCondition.getNow())
// 			)
// 			.offset(pageable.getOffset())
// 			.limit(pageable.getPageSize())
// 			.fetch();
//
// 		JPAQuery<Long> countQuery = queryFactory
// 			.select(cafe.count())
// 			.from(cafe)
// 			.where(
// 				isAbleToStudy(searchCondition.isAbleToStudy()),
// 				regionContains(searchCondition.getRegion()),
// 				maxAllowableStayInLoe(searchCondition.getMaxAllowableStay()),
// 				minBeveragePriceLoe(searchCondition.getMinMenuPrice()),
// 				businessHourBetween(searchCondition.getStartTime(), searchCondition.getEndTime(),
// 					searchCondition.getNow())
// 			);
// 		return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
// 	}
}
