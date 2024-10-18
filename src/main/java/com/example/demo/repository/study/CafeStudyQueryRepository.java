package com.example.demo.repository.study;

import com.example.demo.dto.SliceResponse;
import com.example.demo.dto.study.CafeStudySearchListRequest;
import com.example.demo.implement.study.*;
import com.example.demo.packageex.cafestudy.repository.CafeStudyEntity;
import com.example.demo.util.PagingUtil;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.example.demo.implement.cafe.QCafe.*;
import static com.example.demo.packageex.cafestudy.repository.QCafeStudyEntity.*;

@Repository
@RequiredArgsConstructor
public class CafeStudyQueryRepository {

    private final JPAQueryFactory queryFactory;

    public SliceResponse<CafeStudyEntity> findCafeStudies(CafeStudySearchListRequest request) {
        Pageable pageable = PagingUtil.of(request.getPage(), request.getSizePerPage());

        JPAQuery<CafeStudyEntity> query = queryFactory
            .select(cafeStudyEntity).distinct()
            .from(cafeStudyEntity)
            .join(cafeStudyEntity.cafe, cafe).fetchJoin()
            .where(
                keywordContains(request.getKeyword())
                    .or(cafeStudyNameContains(request.getKeyword())),
                dateEq(request.getDate()),
                cafeStudyTagTypeEq(request.getCafeStudyTagType()),
                hasAllCafeTagTypes(request.getCafeTagTypes()),
                memberCommsEq(request.getMemberComms())
            )
            .orderBy(
                getRecruitmentStatusPriority().asc(),
                cafeStudyEntity.createdDate.desc()
            );

        return SliceResponse.of(PagingUtil.toSlice(query, pageable));
    }

    private NumberExpression<Integer> getRecruitmentStatusPriority() {
        return new CaseBuilder()
            .when(cafeStudyEntity.recruitmentStatus.eq(RecruitmentStatus.OPEN)).then(0)
            .when(cafeStudyEntity.recruitmentStatus.eq(RecruitmentStatus.CLOSED)).then(1)
            .otherwise(3);
    }

    private BooleanExpression memberCommsEq(MemberComms memberComms) {
        return memberComms == null ? null : cafeStudyEntity.memberComms.eq(memberComms);
    }

    private BooleanExpression hasAllCafeTagTypes(List<CafeTagType> cafeTagTypes) {
        if(cafeTagTypes == null || cafeTagTypes.isEmpty()) return null;

        return cafeTagTypes.stream()
            .map(type -> cafe.cafeCafeTags.any().cafeTag.type.eq(type))
            .reduce(BooleanExpression::and)
            .orElse(null);
    }

    private BooleanExpression cafeStudyTagTypeEq(CafeStudyTagType cafeStudyTagType) {
        return cafeStudyTagType == null ? null : cafeStudyEntity.cafeStudyCafeStudyTags.any().cafeStudyTag.type.eq(cafeStudyTagType);
    }

    private BooleanExpression dateEq(LocalDate date) {
        if(date == null) return null;

        return cafeStudyEntity.studyPeriod.startDateTime.year().eq(date.getYear())
            .and(cafeStudyEntity.studyPeriod.startDateTime.month().eq(date.getMonthValue()))
            .and(cafeStudyEntity.studyPeriod.startDateTime.dayOfMonth().eq(date.getDayOfMonth()));
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
        return cafeStudyName == null ? null : Expressions.stringTemplate("function('replace', {0}, ' ', '')", cafeStudyEntity.name)
            .likeIgnoreCase("%" + cafeStudyName.replace(" ", "") + "%");
    }
}
