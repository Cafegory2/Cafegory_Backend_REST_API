package com.example.demo.db.study;

import static com.example.demo.domain.cafe.domain.CafeTagType.*;
import static com.example.demo.domain.study.domain.CafeStudyTagType.*;
import static com.example.demo.domain.study.domain.MemberComms.*;
import static com.example.demo.persister.CafeContextPersister.*;
import static com.example.demo.persister.CafeTagPersister.*;
import static com.example.demo.persister.MemberPersister.*;
import static com.example.demo.persister.StudyConextPersister.*;
import static com.example.demo.persister.StudyTagPersister.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import com.example.demo.auth.dto.SliceResponse;
import com.example.demo.config.FakeTimeUtil;
import com.example.demo.config.JpaTest;
import com.example.demo.db.cafe.CafeEntity;
import com.example.demo.db.cafe.CafeTagEntity;
import com.example.demo.db.member.MemberEntity;
import com.example.demo.domain.cafe.domain.CafeTagType;
import com.example.demo.domain.study.domain.CafeStudyTagType;
import com.example.demo.domain.study.domain.MemberComms;
import com.example.demo.persister.StudyConextPersister;
import com.example.demo.util.TimeUtil;

@Import(StudyQueryDslRepository.class)
class studyQueryDslRepositoryTest extends JpaTest {

	@Autowired
	private StudyQueryDslRepository sut;

	@Autowired
	private TimeUtil timeUtil;

	@ParameterizedTest
	@MethodSource("provideKeywords1")
	@DisplayName("검색어로 카공목록을 조회한다.")
	void find_cafe_studies_by_keyword(String keyword, int expected) {
		//given
		CafeEntity cafe1 = aCafe()
			.includeKeywords("강남", "스타벅스 강남대로점", "서울 강남구 강남대로 456 한석타워 2층 1-2호 (역삼동)").persist();
		CafeEntity cafe2 = aCafe()
			.includeKeywords("강남", "스타벅스 신논현역점", "서울 서초구 강남대로 483 (반포동) 청호빌딩", "카공하기 좋은 카페").persist();

		MemberEntity coordinator = aMember().asCoordinator().persist();

		StudyConextPersister studyWithCafe1 = aStudy().withCafe(cafe1).withMember(coordinator);
		studyWithCafe1.but().withName("카페고리 스터디1").persist();
		studyWithCafe1.but().withName("카공하기 좋은 카페에서 스터디해요").persist();

		StudyConextPersister studyWithCafe2 = aStudy().withCafe(cafe2).withMember(coordinator);
		studyWithCafe2.but().withName("카페고리 스터디2").persist();
		//when
		SliceResponse<CafeStudyEntity> result = sut.findCafeStudies(
			createCafeStudySearchListRequest(keyword, null, null, null, null, 0, 10)
		);
		//then
		assertThat(result.getContent().size()).isEqualTo(expected);
	}

	private static Stream<Arguments> provideKeywords1() {
		return Stream.of(
			//Cafe1과 Cafe2 둘다 관련된 테스트
			Arguments.of("강남", 3),
			Arguments.of("강남 ", 3),
			Arguments.of("스타벅스", 3),
			Arguments.of("스타벅스 ", 3),
			Arguments.of("카페고리", 2),
			Arguments.of("카공하기 좋은 카페", 2),

			//Cafe1과 관련된 테스트
			Arguments.of("스타벅스 강남대로", 2),
			Arguments.of("스타벅스 강남대로점", 2),
			Arguments.of("스타벅스강남대로점", 2),
			Arguments.of("강남구", 2),
			Arguments.of("카페고리 스터디1", 1),
			Arguments.of("카페고리스터디1", 1),

			//Cafe2와 관련된 테스트
			Arguments.of("신논현", 1),
			Arguments.of("스타벅스 신논현역", 1),
			Arguments.of("스타벅스 신논현역점", 1),
			Arguments.of("스타벅스신논현역점", 1),
			Arguments.of("반포동", 1),
			Arguments.of("카페고리 스터디2", 1)
		);
	}

	@ParameterizedTest
	@MethodSource("provideTime1")
	@DisplayName("특정 날짜로 필터링한 카공 목록을 조회한다.")
	void find_cafe_studies_by_start_date_time(
		LocalDateTime startFor1, LocalDateTime endFor1,
		LocalDateTime startFor2, LocalDateTime endFor2,
		LocalDateTime startFor3, LocalDateTime endFor3,
		LocalDate specificDate, int expected
	) {
		//given
		CafeEntity cafe1 = aCafe().includeKeywords("강남").persistWith24For7();
		CafeEntity cafe2 = aCafe().includeKeywords("강남").persistWith24For7();

		MemberEntity coordinator = aMember().asCoordinator().persist();

		aStudy().withCafe(cafe1).withStudyPeriod(startFor1, endFor1).withMember(coordinator).persist();
		aStudy().withCafe(cafe1).withStudyPeriod(startFor2, endFor2).withMember(coordinator).persist();
		aStudy().withCafe(cafe2).withStudyPeriod(startFor3, endFor3).withMember(coordinator).persist();
		//when
		SliceResponse<CafeStudyEntity> result = sut.findCafeStudies(
			createCafeStudySearchListRequest("강남", specificDate, null, null, null, 0, 10)
		);
		//then
		assertThat(result.getContent().size()).isEqualTo(expected);
	}

	private static Stream<Arguments> provideTime1() {
		TimeUtil timeUtil = new FakeTimeUtil();

		return Stream.of(
			Arguments.of(
				// 첫번째 카공 스터디
				timeUtil.localDateTime(2000, 1, 1, 12, 0, 0),
				timeUtil.localDateTime(2000, 1, 1, 14, 0, 0),
				// 두번째 카공 스터디
				timeUtil.localDateTime(2000, 1, 2, 12, 0, 0),
				timeUtil.localDateTime(2000, 1, 2, 12, 0, 0),
				// 세번째 카공 스터디
				timeUtil.localDateTime(2000, 1, 1, 12, 0, 0),
				timeUtil.localDateTime(2000, 1, 1, 14, 0, 0),
				// 특정 시작일
				timeUtil.localDate(2000, 1, 1),
				// 기댓값
				2
			),
			Arguments.of(
				// 첫번째 카공 스터디
				timeUtil.localDateTime(2000, 1, 1, 12, 0, 0),
				timeUtil.localDateTime(2000, 1, 1, 14, 0, 0),
				// 두번째 카공 스터디
				timeUtil.localDateTime(2000, 1, 2, 12, 0, 0),
				timeUtil.localDateTime(2000, 1, 2, 12, 0, 0),
				// 세번째 카공 스터디
				timeUtil.localDateTime(2000, 1, 1, 12, 0, 0),
				timeUtil.localDateTime(2000, 1, 1, 14, 0, 0),
				// 특정 시작일
				timeUtil.localDate(2000, 1, 2),
				// 기댓값
				1
			),
			Arguments.of(
				// 첫번째 카공 스터디
				timeUtil.localDateTime(2000, 1, 1, 12, 0, 0),
				timeUtil.localDateTime(2000, 1, 1, 14, 0, 0),
				// 두번째 카공 스터디
				timeUtil.localDateTime(2000, 1, 2, 12, 0, 0),
				timeUtil.localDateTime(2000, 1, 2, 12, 0, 0),
				// 세번째 카공 스터디
				timeUtil.localDateTime(2000, 1, 1, 12, 0, 0),
				timeUtil.localDateTime(2000, 1, 1, 14, 0, 0),
				// 특정 시작일
				timeUtil.localDate(2000, 1, 3),
				// 기댓값
				0
			),
			Arguments.of(
				timeUtil.localDateTime(2000, 1, 1, 22, 0, 0),
				timeUtil.localDateTime(2000, 1, 1, 23, 59, 59),
				// 두번째 카공 스터디
				timeUtil.localDateTime(2000, 1, 1, 23, 0, 0),
				timeUtil.localDateTime(2000, 1, 2, 1, 0, 0),
				// 세번째 카공 스터디
				timeUtil.localDateTime(2000, 1, 2, 0, 0, 0),
				timeUtil.localDateTime(2000, 1, 2, 2, 0, 0),
				// 특정 시작일
				timeUtil.localDate(2000, 1, 1),
				// 기댓값
				2
			),
			Arguments.of(
				timeUtil.localDateTime(2000, 1, 1, 22, 0, 0),
				timeUtil.localDateTime(2000, 1, 1, 23, 59, 59),
				// 두번째 카공 스터디
				timeUtil.localDateTime(2000, 1, 1, 23, 0, 0),
				timeUtil.localDateTime(2000, 1, 2, 1, 0, 0),
				// 세번째 카공 스터디
				timeUtil.localDateTime(2000, 1, 2, 0, 0, 0),
				timeUtil.localDateTime(2000, 1, 2, 2, 0, 0),
				// 특정 시작일
				timeUtil.localDate(2000, 1, 2),
				// 기댓값
				1
			)
		);
	}

	@ParameterizedTest
	@MethodSource("provideCafeStudyTag1")
	@DisplayName("카공 태그로 필터링한 카공 목록을 조회한다.")
	void find_cafe_studies_by_cafe_study_tag(CafeStudyTagType type, int expected) {
		//given
		CafeEntity cafe1 = aCafe().includeKeywords("강남").persistWith7daysFrom9To21();
		CafeEntity cafe2 = aCafe().includeKeywords("강남").persistWith7daysFrom9To21();

		MemberEntity coordinator = aMember().asCoordinator().persist();

		CafeStudyTagEntity development = aTag().withType(DEVELOPMENT).persist();
		CafeStudyTagEntity design = aTag().withType(DESIGN).persist();

		aStudy().withCafe(cafe1).withMember(coordinator)
			.includeTags(development).persist();
		aStudy().withCafe(cafe1).withMember(coordinator)
			.includeTags(design).persist();

		aStudy().withCafe(cafe2).withMember(coordinator)
			.includeTags(development).persist();
		//when
		SliceResponse<CafeStudyEntity> result = sut.findCafeStudies(
			createCafeStudySearchListRequest("강남", null, type, null, null, 0, 10)
		);
		//then
		assertThat(result.getContent().size()).isEqualTo(expected);
	}

	private static Stream<Arguments> provideCafeStudyTag1() {
		return Stream.of(
			//CafeStudy1, CafeStudy2, CafeStudy3과 관련된 테스트
			Arguments.of(SALES, 0),

			//CafeStudy1, CafeStudy3과 관련된 테스트
			Arguments.of(DEVELOPMENT, 2),

			//CafeStudy2과 관련된 테스트
			Arguments.of(DESIGN, 1)
		);
	}

	@ParameterizedTest
	@MethodSource("provideCafeStudyTag2")
	@DisplayName("하나의 카페 태그로 필터링한 카공 목록을 조회한다.")
	void find_cafe_studies_by_cafe_tag(CafeTagType type, int expected) {
		//given
		CafeTagEntity wifi = aCafeTag().withType(WIFI).persist();
		CafeTagEntity outlet = aCafeTag().withType(OUTLET).persist();

		CafeEntity cafe1 = aCafe().includeTags(wifi).includeKeywords("강남").persistWith7daysFrom9To21();
		CafeEntity cafe2 = aCafe().includeTags(wifi, outlet).includeKeywords("강남").persistWith7daysFrom9To21();

		MemberEntity coordinator = aMember().asCoordinator().persist();

		aStudy().withCafe(cafe1).withMember(coordinator).persist();
		aStudy().withCafe(cafe1).withMember(coordinator).shiftDays(1).persist();
		aStudy().withCafe(cafe2).withMember(coordinator).shiftDays(2).persist();
		//when
		SliceResponse<CafeStudyEntity> result = sut.findCafeStudies(
			createCafeStudySearchListRequest("강남", null, null, List.of(type), null, 0, 10)
		);
		//then
		assertThat(result.getContent().size()).isEqualTo(expected);
	}

	private static Stream<Arguments> provideCafeStudyTag2() {
		return Stream.of(
			//CafeStudy1, CafeStudy2, CafeStudy3과 관련된 테스트
			Arguments.of(WIFI, 3),
			Arguments.of(COMFORTABLE_SEATING, 0),

			//CafeStudy3과 관련된 테스트
			Arguments.of(OUTLET, 1)
		);
	}

	@ParameterizedTest
	@MethodSource("provideCafeStudyTag3")
	@DisplayName("여러개의 카페 태그로 필터링한 카공 목록을 조회한다.")
	void find_cafe_studies_by_cafe_tags(CafeTagType type1, CafeTagType type2, int expected) {
		//given
		CafeTagEntity wifi = aCafeTag().withType(WIFI).persist();
		CafeTagEntity outlet = aCafeTag().withType(OUTLET).persist();
		CafeTagEntity comfortableSeating = aCafeTag().withType(COMFORTABLE_SEATING).persist();

		CafeEntity cafe1 = aCafe().includeTags(wifi, outlet).includeKeywords("강남").persistWith7daysFrom9To21();
		CafeEntity cafe2 = aCafe().includeTags(wifi, comfortableSeating)
			.includeKeywords("강남")
			.persistWith7daysFrom9To21();

		MemberEntity coordinator = aMember().asCoordinator().persist();

		aStudy().withCafe(cafe1).withMember(coordinator).persist();
		aStudy().withCafe(cafe1).withMember(coordinator).shiftDays(1).persist();
		aStudy().withCafe(cafe2).withMember(coordinator).shiftDays(2).persist();
		//when
		SliceResponse<CafeStudyEntity> result = sut.findCafeStudies(
			createCafeStudySearchListRequest("강남", null, null, List.of(type1, type2), null, 0, 10)
		);
		//then
		assertThat(result.getContent().size()).isEqualTo(expected);
	}

	private static Stream<Arguments> provideCafeStudyTag3() {
		return Stream.of(
			//CafeStudy1, CafeStudy2, CafeStudy3과 관련된 테스트
			Arguments.of(WIFI, QUIET, 0),

			//CafeStudy1, CafeStudy2과 관련된 테스트
			Arguments.of(WIFI, OUTLET, 2),

			//CafeStudy3과 관련된 테스트
			Arguments.of(WIFI, COMFORTABLE_SEATING, 1)
		);
	}

	@ParameterizedTest
	@MethodSource("provideMemberComms1")
	@DisplayName("소통 여부로 필터링한 카공 목록을 조회한다.")
	void find_cafe_studies_by_member_communication(MemberComms memberComms, int expected) {
		//given
		CafeEntity cafe1 = aCafe().includeKeywords("강남").persistWith7daysFrom9To21();
		CafeEntity cafe2 = aCafe().includeKeywords("강남").persistWith7daysFrom9To21();

		MemberEntity coordinator = aMember().asCoordinator().persist();

		aStudy().withMemberComms(WELCOME).withCafe(cafe1).withMember(coordinator).persist();
		aStudy().withMemberComms(AVOID).withCafe(cafe1).withMember(coordinator).shiftDays(1).persist();
		aStudy().withMemberComms(WELCOME).withCafe(cafe2).withMember(coordinator).shiftDays(2).persist();
		//when
		SliceResponse<CafeStudyEntity> result = sut.findCafeStudies(
			createCafeStudySearchListRequest("강남", null, null, null, memberComms, 0, 10)
		);
		//then
		assertThat(result.getContent().size()).isEqualTo(expected);
	}

	private static Stream<Arguments> provideMemberComms1() {
		return Stream.of(
			//CafeStudy1, CafeStudy2, CafeStudy3과 관련된 테스트
			Arguments.of(MemberComms.MODERATE, 0),

			//CafeStudy1, CafeStudy3과 관련된 테스트
			Arguments.of(WELCOME, 2),

			//CafeStudy2과 관련된 테스트
			Arguments.of(AVOID, 1)
		);
	}

	@Test
	@DisplayName("카공 목록 조회는 카공 참여 가능 목록을 먼저 보여주고, 카공 생성시간이 최근인 순으로 정렬한다.")
	void show_available_cafe_studies_first() throws Exception {
		//given
		CafeEntity cafe = aCafe().includeKeywords("강남").persistWith24For7();

		MemberEntity coordinator = aMember().asCoordinator().persist();

		CafeStudyEntity study1 = aStudy().withCafe(cafe).withMember(coordinator).persist();
		CafeStudyEntity finishedStudy2 = aStudy().close().shiftDays(1).withCafe(cafe).withMember(coordinator).persist();
		CafeStudyEntity study3 = aStudy().shiftDays(2).withCafe(cafe).withMember(coordinator).persist();
		CafeStudyEntity finishedStudy4 = aStudy().close().shiftDays(3).withCafe(cafe).withMember(coordinator).persist();
		//when
		SliceResponse<CafeStudyEntity> result = sut.findCafeStudies(
			createCafeStudySearchListRequest("강남", null, null, null, null, 0, 10)
		);
		//then
		List<CafeStudyEntity> content = result.getContent();
		assertThat(content)
			.extracting(CafeStudyEntity::getId)
			.containsExactly(study3.getId(), study1.getId(),
				finishedStudy4.getId(), finishedStudy2.getId());
	}

	@Test
	@DisplayName("첫번째 페이지에 대한 카공목록 조회한다.")
	void find_cafe_studies_with_first_page() {
		//given
		CafeEntity cafe = aCafe().includeKeywords("강남").persistWith24For7();
		MemberEntity coordinator = aMember().asCoordinator().persist();

		for (int i = 0; i < 6; i++) {
			aStudy().withCafe(cafe).withMember(coordinator).shiftDays(i).persist();
		}
		//when
		SliceResponse<CafeStudyEntity> result = sut.findCafeStudies(
			createCafeStudySearchListRequest("강남", null, null, null, null, 0, 5)
		);
		//then
		assertThat(result.getContent().size()).isEqualTo(5);
		assertThat(result.isHasNext()).isTrue();
	}

	@Test
	@DisplayName("두번째 페이지에 대한 카공목록 조회한다.")
	void find_cafe_studies_with_second_page() {
		//given
		CafeEntity cafe = aCafe().includeKeywords("강남").persistWith24For7();
		MemberEntity coordinator = aMember().asCoordinator().persist();

		for (int i = 0; i < 11; i++) {
			aStudy().withCafe(cafe).withMember(coordinator).shiftDays(i).persist();
		}
		//when
		SliceResponse<CafeStudyEntity> result = sut.findCafeStudies(
			createCafeStudySearchListRequest("강남", null, null, null, null, 1, 5)
		);
		//then
		assertThat(result.getContent().size()).isEqualTo(5);
		assertThat(result.isHasNext()).isTrue();
	}

	@Test
	@DisplayName("마지막 페이지에 대한 카공목록 조회한다.")
	void find_cafe_studies_with_last_page() {
		//given
		CafeEntity cafe = aCafe().includeKeywords("강남").persistWith24For7();
		MemberEntity coordinator = aMember().asCoordinator().persist();

		for (int i = 0; i < 11; i++) {
			aStudy().withCafe(cafe).withMember(coordinator).shiftDays(i).persist();
		}
		//when
		SliceResponse<CafeStudyEntity> result = sut.findCafeStudies(
			createCafeStudySearchListRequest("강남", null, null, null, null, 2, 5)
		);
		//then
		assertThat(result.getContent().size()).isEqualTo(1);
		assertThat(result.isHasNext()).isFalse();
	}

	@ParameterizedTest
	@MethodSource("provideMultipleFiltering1")
	@DisplayName("다양한 필터링 조합으로 카공 목록을 조회한다.")
	void find_cafe_studies_by_many_different_filtering(
		LocalDate specificDate, List<CafeTagType> cafeTagTypes,
		CafeStudyTagType cafeStudyTagType, MemberComms memberComms, int expected
	) {
		//given
		CafeTagEntity wifi = aCafeTag().withType(WIFI).persist();
		CafeTagEntity outlet = aCafeTag().withType(OUTLET).persist();
		CafeTagEntity comfortableSeating = aCafeTag().withType(COMFORTABLE_SEATING).persist();

		CafeEntity cafe1 = aCafe().includeKeywords("강남").includeTags(wifi, outlet).persistWith7daysFrom9To21();
		CafeEntity cafe2 = aCafe().includeKeywords("강남")
			.includeTags(wifi, comfortableSeating)
			.persistWith7daysFrom9To21();

		MemberEntity coordinator = aMember().asCoordinator().persist();

		CafeStudyTagEntity development = aTag().withType(DEVELOPMENT).persist();
		CafeStudyTagEntity design = aTag().withType(DESIGN).persist();

		aStudy().withStudyPeriod(
				timeUtil.localDateTime(2000, 1, 1, 12, 0, 0),
				timeUtil.localDateTime(2000, 1, 1, 14, 0, 0)
			)
			.withMemberComms(WELCOME)
			.withCafe(cafe1).withMember(coordinator)
			.includeTags(development)
			.persist();

		aStudy().withStudyPeriod(
				timeUtil.localDateTime(2000, 1, 2, 12, 0, 0),
				timeUtil.localDateTime(2000, 1, 2, 14, 0, 0)
			)
			.withMemberComms(AVOID)
			.withCafe(cafe2).withMember(coordinator)
			.includeTags(design)
			.persist();

		aStudy().withStudyPeriod(
				timeUtil.localDateTime(2000, 1, 1, 15, 0, 0),
				timeUtil.localDateTime(2000, 1, 1, 17, 0, 0)
			)
			.withMemberComms(WELCOME)
			.withCafe(cafe1).withMember(coordinator)
			.includeTags(design)
			.persist();
		//when
		SliceResponse<CafeStudyEntity> result = sut.findCafeStudies(
			createCafeStudySearchListRequest("강남", specificDate, cafeStudyTagType, cafeTagTypes, memberComms, 0, 5)
		);
		assertThat(result.getContent().size()).isEqualTo(expected);
	}

	private static Stream<Arguments> provideMultipleFiltering1() {
        /*
        1번 카공 조회 가능 조건
        카페 태그: CafeTagType.WIFI, CafeTagType.OUTLET
        카공 태그: CafeStudyTagType.DEVELOPMENT
        시작일: 2000년 1월 1일
        소통 여부: MemberComms.WELCOME

        2번 카공 조회 가능 조건
        카페 태그: CafeTagType.WIFI, CafeTagType.COMFORTABLE_SEATING
        카공 태그: CafeStudyTagType.DESIGN
        시작일: 2000년 1월 2일
        소통 여부: MemberComms.AVOID

        3번 카공 조회 가능 조건
        카페 태그: CafeTagType.WIFI, CafeTagType.OUTLET
        카공 태그: CafeStudyTagType.DESIGN
        시작일: 2000년 1월 1일
        소통 여부: MemberComms.WELCOME
         */

		TimeUtil timeUtil = new FakeTimeUtil();

		return Stream.of(
			Arguments.of(
				// 특정 시작일
				timeUtil.localDate(2000, 1, 1),
				// 카페 태그
				List.of(WIFI),
				// 카공 태그
				null,
				// 소통 여부
				null,
				// 기댓값
				2
			),
			Arguments.of(
				// 특정 시작일
				timeUtil.localDate(2000, 1, 2),
				// 카페 태그
				Collections.EMPTY_LIST,
				// 카공 태그
				DESIGN,
				// 소통 여부
				null,
				// 기댓값
				1
			),
			Arguments.of(
				// 특정 시작일
				timeUtil.localDate(2000, 1, 1),
				// 카페 태그
				List.of(QUIET),
				// 카공 태그
				null,
				// 소통 여부
				null,
				// 기댓값
				0
			),
			Arguments.of(
				// 특정 시작일
				null,
				// 카페 태그
				Collections.EMPTY_LIST,
				// 카공 태그
				null,
				// 소통 여부
				AVOID,
				// 기댓값
				1
			)
		);
	}

	private CafeStudySearchListRequest createCafeStudySearchListRequest(
		String keyword, LocalDate date, CafeStudyTagType cafeStudyTagType, List<CafeTagType> cafeTagTypes,
		MemberComms memberComms, int page, int sizePerPage) {
		return CafeStudySearchListRequest.builder()
			.keyword(keyword)
			.date(date)
			.cafeStudyTagType(cafeStudyTagType)
			.cafeTagTypes(cafeTagTypes)
			.memberComms(memberComms)
			.page(page)
			.sizePerPage(sizePerPage)
			.build();
	}
}