package com.example.demo.repository.cafe;

import static com.example.demo.persister.CafeContextPersister.*;
import static com.example.demo.persister.CafeTagPersister.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
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
import com.example.demo.cafe.domain.CafeTagType;
import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.cafe.infrastructure.CafeQueryDslRepository;
import com.example.demo.cafe.infrastructure.CafeTagEntity;
import com.example.demo.cafe.presentation.CafeSearchListRequest;
import com.example.demo.config.FakeTimeUtil;
import com.example.demo.config.JpaTest;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.persister.MemberPersister;
import com.example.demo.persister.StudyConextPersister;
import com.example.demo.util.TimeUtil;

@Import(CafeQueryDslRepository.class)
public class CafeQueryDslRepositoryTest extends JpaTest {

	@Autowired
	private CafeQueryDslRepository sut;

	@Autowired
	private TimeUtil timeUtil;

	@ParameterizedTest
	@MethodSource("provideKeywords1")
	@DisplayName("검색어로 카페를 조회한다.")
	void find_cafes_by_keyword(String keyword, int expected) {
		// given
		aCafe()
			.includeKeywords("강남", "스타벅스 강남대로점", "서울 강남구 강남대로 456 한석타워 2층 1-2호 (역삼동)").persistWith7daysFrom9To21();

		aCafe()
			.includeKeywords("강남", "스타벅스 신논현역점", "서울 서초구 강남대로 483 (반포동) 청호빌딩", "카공하기 좋은 카페").persistWith24For7();

		LocalDateTime defaultOpeningDateTime = timeUtil.minLocalDateTime(timeUtil.now());
		LocalDateTime defaultClosingDateTime = timeUtil.maxLocalDateTime(timeUtil.now());

		// when
		SliceResponse<CafeEntity> result = sut.findCafeByRegionAndKeyword(
			createCafeSearchListRequest(
				keyword, defaultOpeningDateTime, defaultClosingDateTime, null, 0, 10
			));

		//then
		assertThat(result.getContent().size()).isEqualTo(expected);

	}

	private static Stream<Arguments> provideKeywords1() {
		return Stream.of(
			//Cafe1과 Cafe2 둘다 관련된 테스트
			Arguments.of("강남", 2),
			Arguments.of("강남 ", 2),
			Arguments.of("스타벅스", 2),
			Arguments.of("스타벅스 ", 2),

			//Cafe1과 관련된 테스트
			Arguments.of("스타벅스 강남대로", 1),
			Arguments.of("스타벅스 강남대로점", 1),
			Arguments.of("스타벅스강남대로점", 1),
			Arguments.of("강남구", 1),

			//Cafe2와 관련된 테스트
			Arguments.of("신논현", 1),
			Arguments.of("스타벅스 신논현역", 1),
			Arguments.of("스타벅스 신논현역점", 1),
			Arguments.of("스타벅스신논현역점", 1),
			Arguments.of("반포동", 1),
			Arguments.of("카공하기 좋은 카페", 1)
		);
	}

	@ParameterizedTest
	@MethodSource("provideTime1")
	@DisplayName("특정 시간으로 필터링한 카페 목록을 조회한다.")
	void find_cafes_by_time(
		LocalDateTime openingDateTime, LocalDateTime closingDateTime, int expected
	) {
		// given
		aCafe().includeKeywords("강남").persistWith7daysFrom9To21();
		aCafe().includeKeywords("강남").persistWith24For7();

		// when
		SliceResponse<CafeEntity> result = sut.findCafeByRegionAndKeyword(
			createCafeSearchListRequest(
				"강남", openingDateTime, closingDateTime, null, 0, 10
			));

		//then
		assertThat(result.getContent().size()).isEqualTo(expected);

	}

	private static Stream<Arguments> provideTime1() {
		TimeUtil timeUtil = new FakeTimeUtil();

		return Stream.of(
			Arguments.of(
				timeUtil.localDateTime(2000, 1, 1, 9, 0, 0),
				timeUtil.localDateTime(2000, 1, 1, 21, 0, 0),
				2
			),
			Arguments.of(
				timeUtil.localDateTime(2000, 1, 1, 21, 1, 1),
				timeUtil.localDateTime(2000, 1, 1, 23, 59, 59),
				1
			),
			Arguments.of(
				timeUtil.localDateTime(2000, 1, 1, 0, 0, 0),
				timeUtil.localDateTime(2000, 1, 1, 23, 59, 59),
				2
			),
			// 필터링 조건의 날짜가 다른 경우
			Arguments.of(
				timeUtil.localDateTime(2000, 1, 1, 9, 0, 0),
				timeUtil.localDateTime(2000, 1, 2, 2, 0, 0),
				2
			)
		);
	}

	@ParameterizedTest
	@MethodSource("provideCafeTag")
	@DisplayName("하나의 카페 태그로 필터링한 카페 목록을 조회한다.")
	void find_cafes_by_cafe_tag(CafeTagType type, int expected) {
		// given
		CafeTagEntity wifiTag = aCafeTag().withType(CafeTagType.WIFI).persist();
		CafeTagEntity outletTag = aCafeTag().withType(CafeTagType.OUTLET).persist();

		aCafe().includeKeywords("강남").includeTags(wifiTag).persistWith7daysFrom9To21();
		aCafe().includeKeywords("강남").includeTags(wifiTag, outletTag).persistWith24For7();

		LocalDateTime defaultOpeningDateTime = timeUtil.minLocalDateTime(timeUtil.now());
		LocalDateTime defaultClosingDateTime = timeUtil.maxLocalDateTime(timeUtil.now());

		// when
		SliceResponse<CafeEntity> result = sut.findCafeByRegionAndKeyword(
			createCafeSearchListRequest(
				"강남", defaultOpeningDateTime, defaultClosingDateTime, List.of(type), 0, 10
			));

		//then
		assertThat(result.getContent().size()).isEqualTo(expected);

	}

	private static Stream<Arguments> provideCafeTag() {
		return Stream.of(
			Arguments.of(CafeTagType.WIFI, 2),
			Arguments.of(CafeTagType.OUTLET, 1),
			Arguments.of(CafeTagType.COMFORTABLE_SEATING, 0)
		);
	}

	@ParameterizedTest
	@MethodSource("provideCafeTag2")
	@DisplayName("여러개의 카페 태그로 필터링한 카페 목록을 조회한다.")
	void find_cafes_by_cafe_tags(List<CafeTagType> types, int expected) {
		// given
		CafeTagEntity wifiTag = aCafeTag().withType(CafeTagType.WIFI).persist();
		CafeTagEntity outletTag = aCafeTag().withType(CafeTagType.OUTLET).persist();
		CafeTagEntity comfortableSeatingTag = aCafeTag()
			.withType(CafeTagType.COMFORTABLE_SEATING)
			.persist();

		aCafe().includeKeywords("강남").includeTags(wifiTag, outletTag).persistWith7daysFrom9To21();
		aCafe().includeKeywords("강남").includeTags(wifiTag, outletTag, comfortableSeatingTag).persistWith24For7();

		LocalDateTime defaultOpeningDateTime = timeUtil.minLocalDateTime(timeUtil.now());
		LocalDateTime defaultClosingDateTime = timeUtil.maxLocalDateTime(timeUtil.now());

		// when
		SliceResponse<CafeEntity> result = sut.findCafeByRegionAndKeyword(
			createCafeSearchListRequest(
				"강남", defaultOpeningDateTime, defaultClosingDateTime, types, 0, 10
			));

		//then
		assertThat(result.getContent().size()).isEqualTo(expected);

	}

	private static Stream<Arguments> provideCafeTag2() {
		return Stream.of(
			Arguments.of(List.of(CafeTagType.WIFI, CafeTagType.OUTLET), 2),
			Arguments.of(List.of(CafeTagType.WIFI, CafeTagType.COMFORTABLE_SEATING), 1),
			Arguments.of(List.of(CafeTagType.WIFI, CafeTagType.QUIET), 0)
		);
	}

	@Test
	@DisplayName("카페 목록 조회는 카공 개수가 많은 순으로 정렬한다.")
	void show_available_cafe_first() throws Exception {
		//given
		CafeEntity cafe1 = aCafe().includeKeywords("강남").persistWith24For7();
		CafeEntity cafe2 = aCafe().includeKeywords("강남").persistWith7daysFrom9To21();
		CafeEntity cafe3 = aCafe().includeKeywords("강남").persistWith7daysFrom9To21();

		MemberEntity coordinator = MemberPersister.aMember().asCoordinator().persist();

		StudyConextPersister.aStudy().withCafe(cafe1).withMember(coordinator).shiftDays(1).persist();
		StudyConextPersister.aStudy().withCafe(cafe1).withMember(coordinator).shiftDays(2).persist();
		StudyConextPersister.aStudy().withCafe(cafe1).withMember(coordinator).shiftDays(3).persist();
		StudyConextPersister.aStudy().withCafe(cafe2).withMember(coordinator).shiftDays(4).persist();

		LocalDateTime defaultOpeningDateTime = timeUtil.minLocalDateTime(timeUtil.now());
		LocalDateTime defaultClosingDateTime = timeUtil.maxLocalDateTime(timeUtil.now());

		// when
		SliceResponse<CafeEntity> result = sut.findCafeByRegionAndKeyword(
			createCafeSearchListRequest(
				"강남", defaultOpeningDateTime, defaultClosingDateTime, null, 0, 10
			));

		//then
		List<CafeEntity> content = result.getContent();
		assertThat(content)
			.extracting(CafeEntity::getId)
			.containsExactlyInAnyOrder(cafe1.getId(), cafe2.getId(), cafe3.getId());

	}

	@Test
	@DisplayName("첫번째 페이지에 대한 카페 목록을 조회한다.")
	void find_cafes_with_first_page() {
		//given
		for (int i = 0; i < 6; i++) {
			aCafe().includeKeywords("강남").persistWith24For7();
		}

		LocalDateTime defaultOpeningDateTime = timeUtil.minLocalDateTime(timeUtil.now());
		LocalDateTime defaultClosingDateTime = timeUtil.maxLocalDateTime(timeUtil.now());

		//when
		SliceResponse<CafeEntity> result = sut.findCafeByRegionAndKeyword(
			createCafeSearchListRequest(
				"강남", defaultOpeningDateTime, defaultClosingDateTime, null, 0, 5
			));

		//then
		assertThat(result.getContent().size()).isEqualTo(5);
		assertThat(result.isHasNext()).isTrue();
	}

	@Test
	@DisplayName("두번째 페이지에 대한 카페 목록을 조회한다.")
	void find_cafes_with_second_page() {
		//given
		for (int i = 0; i < 11; i++) {
			aCafe().includeKeywords("강남").persistWith24For7();
		}

		LocalDateTime defaultOpeningDateTime = timeUtil.minLocalDateTime(timeUtil.now());
		LocalDateTime defaultClosingDateTime = timeUtil.maxLocalDateTime(timeUtil.now());

		//when
		SliceResponse<CafeEntity> result = sut.findCafeByRegionAndKeyword(
			createCafeSearchListRequest(
				"강남", defaultOpeningDateTime, defaultClosingDateTime, null, 1, 5
			));

		//then
		assertThat(result.getContent().size()).isEqualTo(5);
		assertThat(result.isHasNext()).isTrue();
	}

	@Test
	@DisplayName("마지막 페이지에 대한 카페 목록을 조회한다.")
	void find_cafes_with_last_page() {
		//given
		for (int i = 0; i < 11; i++) {
			aCafe().includeKeywords("강남").persistWith24For7();
		}

		LocalDateTime defaultOpeningDateTime = timeUtil.minLocalDateTime(timeUtil.now());
		LocalDateTime defaultClosingDateTime = timeUtil.maxLocalDateTime(timeUtil.now());

		//when
		SliceResponse<CafeEntity> result = sut.findCafeByRegionAndKeyword(
			createCafeSearchListRequest(
				"강남", defaultOpeningDateTime, defaultClosingDateTime, null, 2, 5
			));

		//then
		assertThat(result.getContent().size()).isEqualTo(1);
		assertThat(result.isHasNext()).isFalse();
	}

	@ParameterizedTest
	@MethodSource("provideMultipleFiltering")
	@DisplayName("다양한 필터링 조합으로 카페 목록을 조회한다.")
	void find_cafes_by_many_different_filtering(
		String keyword, LocalDateTime openingDateTime, LocalDateTime closingDateTime, List<CafeTagType> cafeTagTypes,
		int expected
	) {
		//given
		CafeTagEntity wifiTag = aCafeTag().withType(CafeTagType.WIFI).persist();
		CafeTagEntity outletTag = aCafeTag().withType(CafeTagType.OUTLET).persist();
		CafeTagEntity comfortableSeatingTag = aCafeTag().withType(CafeTagType.COMFORTABLE_SEATING).persist();

		aCafe().includeKeywords("강남", "스타벅스 강남대로점", "서울 강남구 강남대로 456 한석타워 2층 1-2호 (역삼동)")
			.includeTags(wifiTag, outletTag)
			.persistWith7daysFrom9To21();

		aCafe().includeKeywords("강남", "스타벅스 신논현역점", "서울 서초구 강남대로 483 (반포동) 청호빌딩", "카공하기 좋은 카페")
			.includeTags(wifiTag, comfortableSeatingTag)
			.persistWith7daysFrom9To21();

		aCafe().includeKeywords("부산", "스타벅스 해운대달맞이점", "부산 해운대구 달맞이길 189 (중동)")
			.includeTags(outletTag, comfortableSeatingTag)
			.persistWith7daysFrom9To21();

		//when
		SliceResponse<CafeEntity> result = sut.findCafeByRegionAndKeyword(
			createCafeSearchListRequest(
				keyword, openingDateTime, closingDateTime, cafeTagTypes, 0, 10
			));

		// then
		assertThat(result.getContent().size()).isEqualTo(expected);
	}

	private static Stream<Arguments> provideMultipleFiltering() {
		/*
		1번 카페 조회 필터링 조건
		키워드: 스타벅스
		오픈 시간: 9시
		마감 시간: 21시
		카페 태그: null

		2번 카페 조회 필터링 조건
		키워드: 강남
		오픈 시간: 9시
		마감 시간: 21시
		카페 태그: WIFI

		3번 카페 조회 필터링 조건
		키워드: 스타벅스
		오픈 시간: 00시
		마감 시간: 23시 59분 59초
		카페 태그: WIFI, COMFORTABLE_SEATING

		4번 카페 조회 필터링 조건
		키워드: 스타벅스
		오픈 시간: 9시
		마감 시간: 익일 2시
		카페 태그: WIFI, OUTLET, COMFORTABLE_SEATING
		 */

		TimeUtil timeUtil = new FakeTimeUtil();

		return Stream.of(
			Arguments.of(
				"스타벅스",
				timeUtil.localDateTime(2000, 1, 1, 9, 0, 0),
				timeUtil.localDateTime(2000, 1, 1, 21, 0, 0),
				null,
				3
			),
			Arguments.of(
				"강남",
				timeUtil.localDateTime(2000, 1, 1, 9, 0, 0),
				timeUtil.localDateTime(2000, 1, 1, 21, 0, 0),
				List.of(CafeTagType.WIFI),
				2
			),
			Arguments.of(
				"스타벅스",
				timeUtil.localDateTime(2000, 1, 1, 9, 0, 0),
				timeUtil.localDateTime(2000, 1, 1, 23, 59, 59),
				List.of(CafeTagType.WIFI, CafeTagType.COMFORTABLE_SEATING),
				1
			),
			Arguments.of(
				"스타벅스",
				timeUtil.localDateTime(2000, 1, 1, 9, 0, 0),
				timeUtil.localDateTime(2000, 1, 2, 2, 0, 0),
				List.of(CafeTagType.WIFI, CafeTagType.OUTLET, CafeTagType.COMFORTABLE_SEATING),
				0
			)
		);

	}

	private CafeSearchListRequest createCafeSearchListRequest(
		String keyword, LocalDateTime openingDateTime, LocalDateTime closingDateTime, List<CafeTagType> cafeTags,
		int page, int sizePerPage
	) {
		return CafeSearchListRequest.builder()
			.keyword(keyword)
			.openingDateTime(openingDateTime)
			.closingDateTime(closingDateTime)
			.cafeTagTypes(cafeTags)
			.page(page)
			.sizePerPage(sizePerPage)
			.build();
	}
}
