package com.example.demo.repository.study;

import com.example.demo.config.FakeTimeUtil;
import com.example.demo.config.JpaTest;
import com.example.demo.helper.*;
import com.example.demo.implement.cafe.Cafe;
import com.example.demo.implement.cafe.CafeTag;
import com.example.demo.implement.member.Member;
import com.example.demo.implement.study.CafeStudy;
import com.example.demo.implement.study.CafeStudyTag;
import com.example.demo.implement.study.CafeStudyTagType;
import com.example.demo.implement.study.CafeTagType;
import com.example.demo.util.TimeUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

//TODO TimeUtil 어떻게 처리할건지 정해야함.
@Import({FakeTimeUtil.class, CafeStudyQueryRepository.class})
class CafeStudyQueryRepositoryTest extends JpaTest {

    @Autowired
    private CafeStudyQueryRepository sut;

    @Autowired
    private CafeStudySaveHelper cafeStudySaveHelper;
    @Autowired
    private CafeSaveHelper cafeSaveHelper;
    @Autowired
    private MemberSaveHelper memberSaveHelper;
    @Autowired
    private CafeKeywordSaveHelper cafeKeywordSaveHelper;
    @Autowired
    private CafeStudyTagSaveHelper cafeStudyTagSaveHelper;
    @Autowired
    private CafeStudyCafeStudyTagSaveHelper cafeStudyCafeStudyTagSaveHelper;
    @Autowired
    private CafeTagSaveHelper cafeTagSaveHelper;
    @Autowired
    private CafeCafeTagSaveHelper cafeCafeTagSaveHelper;
    @Autowired
    private TimeUtil timeUtil;

    @ParameterizedTest
    @MethodSource("provideKeywords1")
    @DisplayName("검색어로 카공목록을 조회한다.")
    void find_cafe_studies_by_keyword(String keyword, int expected) {
        //given
        Cafe cafe1 = cafeSaveHelper.saveCafeWith7daysFrom9To21();
        cafeKeywordSaveHelper.saveCafeKeyword("강남", cafe1);
        cafeKeywordSaveHelper.saveCafeKeyword("스타벅스 강남대로점", cafe1);
        cafeKeywordSaveHelper.saveCafeKeyword("서울 강남구 강남대로 456 한석타워 2층 1-2호 (역삼동)", cafe1);

        Cafe cafe2 = cafeSaveHelper.saveCafeWith7daysFrom9To21();
        cafeKeywordSaveHelper.saveCafeKeyword("강남", cafe2);
        cafeKeywordSaveHelper.saveCafeKeyword("스타벅스 신논현역점", cafe2);
        cafeKeywordSaveHelper.saveCafeKeyword("서울 서초구 강남대로 483 (반포동) 청호빌딩", cafe2);
        cafeKeywordSaveHelper.saveCafeKeyword("카공하기 좋은 카페", cafe2);

        Member member = memberSaveHelper.saveMember();

        LocalDateTime startDateTime = timeUtil.now().plusHours(2);
        LocalDateTime endDateTime = startDateTime.plusHours(2);

        cafeStudySaveHelper.saveCafeStudyWithName(cafe1, member, startDateTime, endDateTime, "카페고리 스터디1");
        cafeStudySaveHelper.saveCafeStudyWithName(cafe1, member, startDateTime.plusHours(5), endDateTime.plusHours(2), "카공하기 좋은 카페에서 스터디해요");

        cafeStudySaveHelper.saveCafeStudyWithName(cafe2, member, startDateTime.plusHours(9), endDateTime.plusHours(2), "카페고리 스터디2");
        //when
        List<CafeStudy> cafes = sut.findCafeStudies(keyword, null, null, null);
        //then
        assertThat(cafes.size()).isEqualTo(expected);
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
        Cafe cafe1 = cafeSaveHelper.saveCafeWith24For7();
        cafeKeywordSaveHelper.saveCafeKeyword("강남", cafe1);

        Cafe cafe2 = cafeSaveHelper.saveCafeWith24For7();
        cafeKeywordSaveHelper.saveCafeKeyword("강남", cafe2);

        Member member = memberSaveHelper.saveMember();

        cafeStudySaveHelper.saveCafeStudy(cafe1, member, startFor1, endFor1);
        cafeStudySaveHelper.saveCafeStudy(cafe1, member, startFor2, endFor2);

        cafeStudySaveHelper.saveCafeStudy(cafe2, member, startFor3, endFor3);
        //when
        List<CafeStudy> cafes = sut.findCafeStudies("강남", specificDate, null, null);
        //then
        assertThat(cafes.size()).isEqualTo(expected);
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
        Cafe cafe1 = cafeSaveHelper.saveCafeWith7daysFrom9To21();
        cafeKeywordSaveHelper.saveCafeKeyword("강남", cafe1);

        Cafe cafe2 = cafeSaveHelper.saveCafeWith7daysFrom9To21();
        cafeKeywordSaveHelper.saveCafeKeyword("강남", cafe2);

        Member member = memberSaveHelper.saveMember();

        LocalDateTime startDateTime = timeUtil.now().plusHours(2);
        LocalDateTime endDateTime = startDateTime.plusHours(2);

        CafeStudyTag cafeStudyTag1 = cafeStudyTagSaveHelper.saveCafeStudyTag(CafeStudyTagType.DEVELOPMENT);
        CafeStudyTag cafeStudyTag2 = cafeStudyTagSaveHelper.saveCafeStudyTag(CafeStudyTagType.DESIGN);

        CafeStudy cafeStudy1 = cafeStudySaveHelper.saveCafeStudy(cafe1, member, startDateTime, endDateTime);
        cafeStudyCafeStudyTagSaveHelper.saveCafeStudyCafeStudyTag(cafeStudy1, cafeStudyTag1);
        CafeStudy cafeStudy2 = cafeStudySaveHelper.saveCafeStudy(cafe1, member, startDateTime.plusHours(5), endDateTime.plusHours(2));
        cafeStudyCafeStudyTagSaveHelper.saveCafeStudyCafeStudyTag(cafeStudy2, cafeStudyTag2);

        CafeStudy cafeStudy3 = cafeStudySaveHelper.saveCafeStudy(cafe2, member, startDateTime.plusHours(9), endDateTime.plusHours(2));
        cafeStudyCafeStudyTagSaveHelper.saveCafeStudyCafeStudyTag(cafeStudy3, cafeStudyTag1);
        //when
        List<CafeStudy> cafes = sut.findCafeStudies("강남", null, type, null);
        //then
        assertThat(cafes.size()).isEqualTo(expected);
    }

    private static Stream<Arguments> provideCafeStudyTag1() {
        return Stream.of(
            //CafeStudy1, CafeStudy2, CafeStudy3과 관련된 테스트
            Arguments.of(CafeStudyTagType.SALES, 0),

            //CafeStudy1, CafeStudy3과 관련된 테스트
            Arguments.of(CafeStudyTagType.DEVELOPMENT, 2),

            //CafeStudy2과 관련된 테스트
            Arguments.of(CafeStudyTagType.DESIGN, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("provideCafeStudyTag2")
    @DisplayName("하나의 카페 태그로 필터링한 카공 목록을 조회한다.")
    void find_cafe_studies_by_cafe_tag(CafeTagType type, int expected) {
        //given
        CafeTag cafeTag1 = cafeTagSaveHelper.saveCafeTag(CafeTagType.WIFI);
        CafeTag cafeTag2 = cafeTagSaveHelper.saveCafeTag(CafeTagType.OUTLET);

        Cafe cafe1 = cafeSaveHelper.saveCafeWith7daysFrom9To21();
        cafeKeywordSaveHelper.saveCafeKeyword("강남", cafe1);
        cafeCafeTagSaveHelper.saveCafeCafeTag(cafe1, cafeTag1);

        Cafe cafe2 = cafeSaveHelper.saveCafeWith7daysFrom9To21();
        cafeKeywordSaveHelper.saveCafeKeyword("강남", cafe2);
        cafeCafeTagSaveHelper.saveCafeCafeTag(cafe2, cafeTag1);
        cafeCafeTagSaveHelper.saveCafeCafeTag(cafe2, cafeTag2);

        Member member = memberSaveHelper.saveMember();

        LocalDateTime startDateTime = timeUtil.now().plusHours(2);
        LocalDateTime endDateTime = startDateTime.plusHours(2);

        cafeStudySaveHelper.saveCafeStudy(cafe1, member, startDateTime, endDateTime);
        cafeStudySaveHelper.saveCafeStudy(cafe1, member, startDateTime.plusHours(5), endDateTime.plusHours(2));

        cafeStudySaveHelper.saveCafeStudy(cafe2, member, startDateTime.plusHours(9), endDateTime.plusHours(2));
        //when
        List<CafeStudy> cafes = sut.findCafeStudies("강남", null, null, type);
        //then
        assertThat(cafes.size()).isEqualTo(expected);
    }

    private static Stream<Arguments> provideCafeStudyTag2() {
        return Stream.of(
            //CafeStudy1, CafeStudy2, CafeStudy3과 관련된 테스트
            Arguments.of(CafeTagType.WIFI, 3),
            Arguments.of(CafeTagType.COMFORTABLE_SEATING, 0),

            //CafeStudy3과 관련된 테스트
            Arguments.of(CafeTagType.OUTLET, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("provideCafeStudyTag3")
    @DisplayName("여러개의 카페 태그들로 필터링한 카공 목록을 조회한다.")
    void find_cafe_studies_by_cafe_tags(CafeTagType type1, CafeTagType type2, int expected) {
        //given
        CafeTag cafeTag1 = cafeTagSaveHelper.saveCafeTag(CafeTagType.WIFI);
        CafeTag cafeTag2 = cafeTagSaveHelper.saveCafeTag(CafeTagType.OUTLET);
        CafeTag cafeTag3 = cafeTagSaveHelper.saveCafeTag(CafeTagType.COMFORTABLE_SEATING);
        CafeTag cafeTag4 = cafeTagSaveHelper.saveCafeTag(CafeTagType.QUIET);

        Cafe cafe1 = cafeSaveHelper.saveCafeWith7daysFrom9To21();
        cafeKeywordSaveHelper.saveCafeKeyword("강남", cafe1);
        cafeCafeTagSaveHelper.saveCafeCafeTag(cafe1, cafeTag1);
        cafeCafeTagSaveHelper.saveCafeCafeTag(cafe1, cafeTag2);

        Cafe cafe2 = cafeSaveHelper.saveCafeWith7daysFrom9To21();
        cafeKeywordSaveHelper.saveCafeKeyword("강남", cafe2);
        cafeCafeTagSaveHelper.saveCafeCafeTag(cafe2, cafeTag1);
        cafeCafeTagSaveHelper.saveCafeCafeTag(cafe2, cafeTag3);

        Member member = memberSaveHelper.saveMember();

        LocalDateTime startDateTime = timeUtil.now().plusHours(2);
        LocalDateTime endDateTime = startDateTime.plusHours(2);

        cafeStudySaveHelper.saveCafeStudy(cafe1, member, startDateTime, endDateTime);
        cafeStudySaveHelper.saveCafeStudy(cafe1, member, startDateTime.plusHours(5), endDateTime.plusHours(2));

        cafeStudySaveHelper.saveCafeStudy(cafe2, member, startDateTime.plusHours(9), endDateTime.plusHours(2));
        //when
        List<CafeStudy> cafes = sut.findCafeStudies("강남", null, null, type1, type2);
        //then
        assertThat(cafes.size()).isEqualTo(expected);
    }

    private static Stream<Arguments> provideCafeStudyTag3() {
        return Stream.of(
            //CafeStudy1, CafeStudy2, CafeStudy3과 관련된 테스트
            Arguments.of(CafeTagType.WIFI, CafeTagType.QUIET, 0),

            //CafeStudy1, CafeStudy2과 관련된 테스트
            Arguments.of(CafeTagType.WIFI, CafeTagType.OUTLET, 2),

            //CafeStudy3과 관련된 테스트
            Arguments.of(CafeTagType.WIFI, CafeTagType.COMFORTABLE_SEATING, 1)
        );
    }
}