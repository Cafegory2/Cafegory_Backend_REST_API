package com.example.demo.service.study;

import static com.example.demo.exception.ExceptionType.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.config.ServiceTest;
import com.example.demo.dto.study.CafeStudyCreateRequest;
import com.example.demo.exception.CafegoryException;
import com.example.demo.helper.CafeSaveHelper;
import com.example.demo.helper.CafeStudySaveHelper;
import com.example.demo.helper.MemberSaveHelper;
import com.example.demo.implement.cafe.CafeEntity;
import com.example.demo.implement.member.MemberEntity;
import com.example.demo.implement.study.CafeStudyEntity;
import com.example.demo.implement.study.MemberComms;
import com.example.demo.util.TimeUtil;

class CafeStudyServiceTest extends ServiceTest {

	@Autowired
	private CafeStudyService sut;
	@Autowired
	private CafeSaveHelper cafeSaveHelper;
	@Autowired
	private MemberSaveHelper memberSaveHelper;
	@Autowired
	private TimeUtil timeUtil;
	@Autowired
	private CafeStudySaveHelper cafeStudySaveHelper;

	//	@Autowired
	//	private StudyOnceRepository studyOnceRepository;
	//	@Autowired
	//	private StudyMemberRepository studyMemberRepository;
	//	@Autowired
	//	private ThumbnailImageSaveHelper thumbnailImageSaveHelper;

	//
	//	@Test
	//	@DisplayName("정상 목록 조회 테스트")
	//	void searchStudyByDto() {
	//
	//	}

	private CafeStudyCreateRequest makeCafeStudyCreateRequest(LocalDateTime start, LocalDateTime end, long cafeId) {
		return CafeStudyCreateRequest.builder()
			.name("테스트 스터디")
			.cafeId(cafeId)
			.startDateTime(start)
			.endDateTime(end)
			.memberComms(MemberComms.WELCOME)
			.maxParticipants(4)
			.introduction("스터디 소개글")
			.build();

	}

	//	@Test
	//	@DisplayName("회원이 아닐떄 카공을 조회하면 카공의 참석여부는 false를 반환한다.")
	//	void searchByStudyId_when_not_member() {
	//		//TODO 테스트 코드, 프로덕션 코드 수정 필요
	//		LocalDateTime start = LocalDateTime.now().plusHours(3).plusMinutes(1);
	//		LocalDateTime end = start.plusHours(1);
	//		long cafeId = cafeSaveHelper.saveCafeWith24For7().getId();
	//		ThumbnailImage thumbnailImage = thumbnailImageSaveHelper.saveThumbnailImage();
	//		long leaderId = memberSaveHelper.saveMember(thumbnailImage).getId();
	//		StudyOnceCreateRequest studyOnceCreateRequest = makeStudyOnceCreateRequest(start, end, cafeId);
	//		StudyOnceCreateResponse result = sut.createStudy(leaderId, studyOnceCreateRequest);
	//
	//		StudyOnceSearchResponse studyOnceSearchResponse = sut.searchByStudyId(result.getStudyOnceId());
	//
	//		assertThat(studyOnceSearchResponse.isAttendance()).isFalse();
	//	}
	//
	//	@Test
	//	@DisplayName("회원이 카공을 조회할때 카공 멤버라면 참석여부는 true를 반환한다.")
	//	void searchStudyOnceWithMemberParticipation_when_takes_attendance() {
	//		//TODO 테스트 코드, 프로덕션 코드 수정 필요
	//		LocalDateTime start = LocalDateTime.now().plusHours(4);
	//		LocalDateTime end = start.plusHours(4);
	//		long cafeId = cafeSaveHelper.saveCafeWith24For7().getId();
	//		StudyOnceCreateRequest studyOnceCreateRequest = makeStudyOnceCreateRequest(start, end, cafeId);
	//		ThumbnailImage thumbnailImage = thumbnailImageSaveHelper.saveThumbnailImage();
	//		long leaderId = memberSaveHelper.saveMember(thumbnailImage).getId();
	//		StudyOnceCreateResponse searchResponse = sut.createStudy(leaderId, studyOnceCreateRequest);
	//		long studyOnceId = searchResponse.getStudyOnceId();
	//		long memberId = memberSaveHelper.saveMember(thumbnailImage).getId();
	//		sut.tryJoin(memberId, studyOnceId);
	//
	//		StudyOnceSearchResponse response = sut.searchStudyOnceWithMemberParticipation(
	//			studyOnceId, memberId);
	//
	//		assertThat(response.isAttendance()).isTrue();
	//	}
	//
	//	@Test
	//	@DisplayName("회원이 카공을 조회할때 카공 멤버가 아니라면 참석여부는 false를 반환한다.")
	//	void searchStudyOnceWithMemberParticipation_when_not_take_attendance() {
	//		//TODO 테스트 코드, 프로덕션 코드 수정 필요
	//		LocalDateTime start = LocalDateTime.now().plusHours(4);
	//		LocalDateTime end = start.plusHours(4);
	//		long cafeId = cafeSaveHelper.saveCafeWith24For7().getId();
	//		StudyOnceCreateRequest studyOnceCreateRequest = makeStudyOnceCreateRequest(start, end, cafeId);
	//		ThumbnailImage thumbnailImage = thumbnailImageSaveHelper.saveThumbnailImage();
	//		long leaderId = memberSaveHelper.saveMember(thumbnailImage).getId();
	//		StudyOnceCreateResponse searchResponse = sut.createStudy(leaderId, studyOnceCreateRequest);
	//		long studyOnceId = searchResponse.getStudyOnceId();
	//		long memberId = memberSaveHelper.saveMember(thumbnailImage).getId();
	//
	//		StudyOnceSearchResponse response = sut.searchStudyOnceWithMemberParticipation(
	//			studyOnceId, memberId);
	//
	//		assertThat(response.isAttendance()).isFalse();
	//	}

	@Test
	@DisplayName("카공 시작시간이 23시이고 종료시간이 24시(23시 59분 59초)이면 카공이 생성된다.")
	void exception_case() {
		//given
		LocalDateTime now = timeUtil.localDateTime(2000, 1, 1, 0, 0, 0);
		LocalDateTime start = timeUtil.localDateTime(2000, 1, 1, 23, 0, 0);
		LocalDateTime end = timeUtil.localDateTime(2000, 1, 1, 23, 59, 59);

		MemberEntity leader = memberSaveHelper.saveMember();
		CafeEntity cafeEntity = cafeSaveHelper.saveCafeWith24For7();
		CafeStudyCreateRequest cafeStudyCreateRequest = makeCafeStudyCreateRequest(start, end, cafeEntity.getId());
		//then
		assertDoesNotThrow(() ->
			sut.createStudy(leader.getId(), now, cafeStudyCreateRequest));
	}

	@Test
	@DisplayName("카공 시작은 현재 시간으로부터 최소 1시간 이후여야 한다.")
	void study_starts_1hours_after_now() {
		//given
		MemberEntity coordinator = memberSaveHelper.saveMember();
		LocalDateTime start = timeUtil.now().plusHours(1);
		LocalDateTime end = start.plusHours(1);
		CafeEntity cafeEntity = cafeSaveHelper.saveCafeWith24For7();
		CafeStudyCreateRequest cafeStudyCreateRequest = makeCafeStudyCreateRequest(start, end, cafeEntity.getId());
		//then
		assertDoesNotThrow(() -> sut.createStudy(coordinator.getId(), timeUtil.now(), cafeStudyCreateRequest));
	}

	@Test
	@DisplayName("카공 시작은 현재 시간으로부터 1시간 이전일 수 없다.")
	void study_starts_1hours_before_now() {
		//given
		MemberEntity coordinator = memberSaveHelper.saveMember();
		LocalDateTime start = timeUtil.now().plusHours(1).minusMinutes(1);
		LocalDateTime end = start.plusHours(1);
		CafeEntity cafeEntity = cafeSaveHelper.saveCafeWith24For7();
		CafeStudyCreateRequest cafeStudyCreateRequest = makeCafeStudyCreateRequest(start, end, cafeEntity.getId());
		//then
		assertThatThrownBy(
			() -> sut.createStudy(coordinator.getId(), timeUtil.now(), cafeStudyCreateRequest)).isInstanceOf(
			CafegoryException.class).hasMessage(STUDY_ONCE_WRONG_START_TIME.getErrorMessage());
	}

	@Test
	@DisplayName("카공 시작은 현재 날짜로부터 한달 이내여야 한다.")
	void study_start_date_before_one_month() {
		//given
		MemberEntity coordinator = memberSaveHelper.saveMember();
		LocalDateTime start = timeUtil.now().plusMonths(1);
		LocalDateTime end = start.plusHours(1);
		CafeEntity cafeEntity = cafeSaveHelper.saveCafeWith24For7();
		CafeStudyCreateRequest cafeStudyCreateRequest = makeCafeStudyCreateRequest(start, end, cafeEntity.getId());
		//then
		assertDoesNotThrow(() -> sut.createStudy(coordinator.getId(), timeUtil.now(), cafeStudyCreateRequest));
	}

	@Test
	@DisplayName("카공 시작은 현재 날짜로부터 한달 이내여야 한다.")
	void study_start_date_after_one_month() {
		//given
		MemberEntity coordinator = memberSaveHelper.saveMember();
		LocalDateTime start = timeUtil.now().plusMonths(1).plusDays(1);
		LocalDateTime end = start.plusHours(1);
		CafeEntity cafeEntity = cafeSaveHelper.saveCafeWith24For7();
		CafeStudyCreateRequest cafeStudyCreateRequest = makeCafeStudyCreateRequest(start, end, cafeEntity.getId());
		//then
		assertThatThrownBy(
			() -> sut.createStudy(coordinator.getId(), timeUtil.now(), cafeStudyCreateRequest)).isInstanceOf(
			CafegoryException.class).hasMessage(CAFE_STUDY_WRONG_START_DATE.getErrorMessage());
	}

	//	@Test
	//	@DisplayName("카공 진행시간이 1시간 미만일 수 없다.")
	//	void study_duration_must_be_at_least_1hour() {
	//		//given
	//		LocalDateTime start = NOW.plusHours(4);
	//		LocalDateTime end = start.plusMinutes(59).plusSeconds(59);
	//		ThumbnailImage thumbnailImage = thumbnailImageSaveHelper.saveThumbnailImage();
	//		Member leader = memberSaveHelper.saveMember(thumbnailImage);
	//		Cafe cafe = cafeSaveHelper.saveCafeWith24For7();
	//		StudyOnceCreateRequest request = makeStudyOnceCreateRequest(start, end, cafe.getId());
	//		//then
	//		assertThatThrownBy(() -> sut.createStudy(leader.getId(), request))
	//			.isInstanceOf(CafegoryException.class)
	//			.hasMessage(STUDY_ONCE_SHORT_DURATION.getErrorMessage());
	//	}
	//
	//	@Test
	//	@DisplayName("카공 진행시간이 5시간 초과일 수 없다.")
	//	void study_duration_can_not_exceed_5hours() {
	//		//given
	//		LocalDateTime start = NOW.plusHours(4);
	//		LocalDateTime end = start.plusHours(5).plusSeconds(1);
	//		ThumbnailImage thumbnailImage = thumbnailImageSaveHelper.saveThumbnailImage();
	//		Member leader = memberSaveHelper.saveMember(thumbnailImage);
	//		Cafe cafe = cafeSaveHelper.saveCafeWith24For7();
	//		StudyOnceCreateRequest request = makeStudyOnceCreateRequest(start, end, cafe.getId());
	//		//then
	//		assertThatThrownBy(() -> sut.createStudy(leader.getId(), request))
	//			.isInstanceOf(CafegoryException.class)
	//			.hasMessage(STUDY_ONCE_LONG_DURATION.getErrorMessage());
	//	}
	//
	//	@ParameterizedTest
	//	@ValueSource(ints = {1, 5})
	//	@DisplayName("카공 진행시간은 최소 1시간에서 최대 5시간까지 가능하다.")
	//	void check_study_duration_limits(int duration) {
	//		//given
	//		LocalDateTime start = NOW.plusHours(4);
	//		LocalDateTime end = start.plusHours(duration);
	//		ThumbnailImage thumbnailImage = thumbnailImageSaveHelper.saveThumbnailImage();
	//		Member leader = memberSaveHelper.saveMember(thumbnailImage);
	//		Cafe cafe = cafeSaveHelper.saveCafeWith24For7();
	//		StudyOnceCreateRequest studyOnceCreateRequest = makeStudyOnceCreateRequest(start, end, cafe.getId());
	//		//then
	//		assertDoesNotThrow(() -> sut.createStudy(leader.getId(), studyOnceCreateRequest));
	//	}
	//
	// @ParameterizedTest
	// @MethodSource("provideLocalDateTime1")
	// @DisplayName("같은 시간대에 카공을 여러개 만들 수 없다.")
	// void can_not_schedule_several_study_at_the_same_time(LocalDateTime testStartTime, LocalDateTime testEndTime) {
	// 	//given
	// 	LocalDateTime standard = NOW.plusYears(1);
	// 	LocalDateTime start = standard.plusHours(4);
	// 	LocalDateTime end = start.plusHours(5);
	//
	// 	Member leader = memberSaveHelper.saveMember();
	// 	Cafe cafe1 = cafeSaveHelper.saveCafeWith24For7();
	// 	Cafe cafe2 = cafeSaveHelper.saveCafeWith24For7();
	// 	studyOnceSaveHelper.saveStudyOnceWithTime(cafe1, leader, start, end);
	// 	StudyOnceCreateRequest request = makeStudyOnceCreateRequest(testStartTime, testEndTime, cafe2.getId());
	// 	//then
	// 	assertThatThrownBy(() -> sut.createStudy(leader.getId(), request))
	// 		.isInstanceOf(CafegoryException.class)
	// 		.hasMessage(STUDY_ONCE_CONFLICT_TIME.getErrorMessage());
	// }
	//
	// static Stream<Arguments> provideLocalDateTime1() {
	// 	LocalDateTime standard = NOW.plusYears(1);
	// 	LocalDateTime start = standard.plusHours(4);
	// 	return Stream.of(Arguments.of(start, start.plusHours(5)), Arguments.of(start.minusHours(2), start),
	// 		Arguments.of(start.plusHours(5), start.plusHours(9)));
	// }

	//	@ParameterizedTest
	//	@MethodSource("provideLocalDateTime2")
	//	@DisplayName("같은 시간대에 여러개의 카공에 참여할 수 없다.")
	//	void member_can_not_join_several_study_simultaneously(LocalDateTime testStartTime, LocalDateTime testEndTime) {
	//		//given
	//		LocalDateTime standard = NOW.plusYears(1);
	//		LocalDateTime start = standard.plusHours(4);
	//		LocalDateTime end = start.plusHours(5);
	//
	//		ThumbnailImage thumbnailImage = thumbnailImageSaveHelper.saveThumbnailImage();
	//		Member leader1 = memberSaveHelper.saveMember(thumbnailImage);
	//		Member leader2 = memberSaveHelper.saveMember(thumbnailImage);
	//		Member member = memberSaveHelper.saveMember(thumbnailImage);
	//		Cafe cafe1 = cafeSaveHelper.saveCafeWith24For7();
	//		Cafe cafe2 = cafeSaveHelper.saveCafeWith24For7();
	//		StudyOnce studyOnce1 = studyOnceSaveHelper.saveStudyOnceWithTime(cafe1, leader1, start, end);
	//		StudyOnce studyOnce2 = studyOnceSaveHelper.saveStudyOnceWithTime(cafe2, leader2, testStartTime, testEndTime);
	//		sut.tryJoin(member.getId(), studyOnce1.getId());
	//		//then
	//		assertThatThrownBy(() -> sut.tryJoin(member.getId(), studyOnce2.getId()))
	//			.isInstanceOf(CafegoryException.class)
	//			.hasMessage(STUDY_ONCE_CONFLICT_TIME.getErrorMessage());
	//	}
	//
	//	static Stream<Arguments> provideLocalDateTime2() {
	//		LocalDateTime standard = NOW.plusYears(1);
	//		LocalDateTime start = standard.plusHours(4);
	//		return Stream.of(
	//			Arguments.of(start, start.plusHours(5)),
	//			Arguments.of(start.minusHours(2), start),
	//			Arguments.of(start.plusHours(5), start.plusHours(9))
	//		);
	//	}

	@ParameterizedTest()
	@MethodSource("provideStartAndEndDateTime1")
	@DisplayName("카페 영업시간 밖의 시간에 카공을 만들 수 없다.")
	void study_can_not_start_outside_cafe_business_hours(LocalDateTime start, LocalDateTime end) {
		//given
		LocalDateTime now = timeUtil.localDateTime(2000, 1, 1, 0, 0, 0);

		CafeEntity cafeEntity = cafeSaveHelper.saveCafeWith7daysFrom9To21();
		MemberEntity leader = memberSaveHelper.saveMember();
		CafeStudyCreateRequest cafeStudyCreateRequest = makeCafeStudyCreateRequest(start, end, cafeEntity.getId());
		//then
		assertThatThrownBy(() -> sut.createStudy(leader.getId(), now, cafeStudyCreateRequest)).isInstanceOf(
			CafegoryException.class).hasMessage(STUDY_ONCE_CREATE_BETWEEN_CAFE_BUSINESS_HOURS.getErrorMessage());
	}

	static Stream<Arguments> provideStartAndEndDateTime1() {
		return Stream.of(Arguments.of(LocalDateTime.of(2000, 1, 1, 8, 59, 59), LocalDateTime.of(2000, 1, 1, 10, 0)),
			Arguments.of(LocalDateTime.of(2000, 1, 1, 8, 0), LocalDateTime.of(2000, 1, 1, 9, 0, 0)),
			Arguments.of(LocalDateTime.of(2000, 1, 1, 20, 0), LocalDateTime.of(2000, 1, 1, 21, 0, 1)),
			Arguments.of(LocalDateTime.of(2000, 1, 1, 20, 59, 59), LocalDateTime.of(2000, 1, 1, 22, 0)));
	}

	@ParameterizedTest()
	@MethodSource("provideStartAndEndDateTime2")
	@DisplayName("카페 영업시간 내의 시간에 카공을 만들 수 있다.")
	void study_can_start_between_cafe_business_hours(LocalDateTime start, LocalDateTime end) {
		//given
		LocalDateTime now = timeUtil.localDateTime(2000, 1, 1, 0, 0, 0);

		CafeEntity cafeEntity = cafeSaveHelper.saveCafeWith7daysFrom9To21();
		MemberEntity leader = memberSaveHelper.saveMember();
		CafeStudyCreateRequest studyOnceCreateRequest = makeCafeStudyCreateRequest(start, end, cafeEntity.getId());
		//then
		assertDoesNotThrow(() -> sut.createStudy(leader.getId(), now, studyOnceCreateRequest));
	}

	static Stream<Arguments> provideStartAndEndDateTime2() {
		return Stream.of(Arguments.of(LocalDateTime.of(2000, 1, 1, 9, 0), LocalDateTime.of(2000, 1, 1, 10, 0)),
			Arguments.of(LocalDateTime.of(2000, 1, 1, 20, 0), LocalDateTime.of(2000, 1, 1, 21, 0)));
	}

	@Test
	@DisplayName("카공장은 카공을 삭제할 수 있다.")
	void coordinator_can_delete_study() {
		//given
		CafeEntity cafeEntity = cafeSaveHelper.saveCafe();
		MemberEntity coordinator = memberSaveHelper.saveMember();
		LocalDateTime start = LocalDateTime.of(2000, 1, 1, 23, 0, 0);
		LocalDateTime end = LocalDateTime.of(2000, 1, 1, 23, 0, 0);
		CafeStudyEntity cafeStudy = cafeStudySaveHelper.saveCafeStudy(cafeEntity, coordinator, start, end);

		//then
		assertDoesNotThrow(() -> sut.deleteStudy(coordinator.getId(), cafeStudy.getId(), timeUtil.now()));
	}

	@Test
	@DisplayName("카공장이 아니라면 카공을 삭제할 수 없다.")
	void non_coordinator_can_not_delete_study() {
		//given
		CafeEntity cafeEntity = cafeSaveHelper.saveCafe();
		MemberEntity coordinator = memberSaveHelper.saveMember("coordinator@gmail.com");
		MemberEntity member = memberSaveHelper.saveMember("member@gmail.com");
		LocalDateTime start = LocalDateTime.of(2000, 1, 1, 23, 0, 0);
		LocalDateTime end = LocalDateTime.of(2000, 1, 1, 23, 0, 0);
		CafeStudyEntity cafeStudy = cafeStudySaveHelper.saveCafeStudy(cafeEntity, coordinator, start, end);

		//then
		assertThatThrownBy(
			() -> sut.deleteStudy(member.getId(), cafeStudy.getId(), timeUtil.now()))
			.isInstanceOf(CafegoryException.class)
			.hasMessage(CAFE_STUDY_INVALID_LEADER.getErrorMessage());
	}
}

//	@Test
//	@DisplayName("카공이 시작하기전에 카공 참여를 취소할 수 있다.")
//	void member_can_cancel_study_before_starts() {
//		//given
//		LocalDateTime start = NOW.plusHours(4);
//		LocalDateTime end = start.plusHours(4);
//		ThumbnailImage thumbnailImage = thumbnailImageSaveHelper.saveThumbnailImage();
//		Member leader = memberSaveHelper.saveMember(thumbnailImage);
//		Member member = memberSaveHelper.saveMember(thumbnailImage);
//		Cafe cafe = cafeSaveHelper.saveCafeWith24For7();
//		StudyOnce studyOnce = studyOnceSaveHelper.saveStudyOnceWithTime(cafe, leader, start, end);
//		sut.tryJoin(member.getId(), studyOnce.getId());
//		//when
//		sut.tryQuit(member.getId(), studyOnce.getId());
//		//then
//		List<StudyMember> result = studyMemberRepository.findAll();
//		assertThat(result.size()).isEqualTo(1);
//	}
//
//	@Test
//	@DisplayName("카공장은 카공 시작 10분 후 출석체크를 할 수 있다.")
//	void leader_checks_attendance_10min_after_start() {
//		//given
//		LocalDateTime start = NOW.plusHours(4);
//		LocalDateTime end = start.plusHours(4);
//		Cafe cafe = cafeSaveHelper.saveCafeWith24For7();
//		ThumbnailImage thumbnailImage = thumbnailImageSaveHelper.saveThumbnailImage();
//		Member leader = memberSaveHelper.saveMember(thumbnailImage);
//		Member member = memberSaveHelper.saveMember(thumbnailImage);
//		StudyOnce studyOnce = studyOnceSaveHelper.saveStudyOnceWithTime(cafe, leader, start, end);
//		sut.tryJoin(member.getId(), studyOnce.getId());
//		LocalDateTime attendanceUpdateTime = start.plusMinutes(10);
//		//when
//		sut.updateAttendance(leader.getId(), studyOnce.getId(), member.getId(), NO, attendanceUpdateTime);
//		//then
//		StudyMember studyMember = studyMemberRepository.findById(
//			new StudyMemberId(member.getId(), studyOnce.getId())
//		).get();
//		assertThat(studyMember.getAttendance()).isEqualTo(NO);
//	}
//
//	@Test
//	@DisplayName("카공 시작 10분 전에 출석체크를 할 수 없다.")
//	void leader_can_not_check_attendance_10min_before_start() {
//		//given
//		LocalDateTime start = NOW.plusHours(4);
//		LocalDateTime end = start.plusHours(4);
//		Cafe cafe = cafeSaveHelper.saveCafeWith24For7();
//		ThumbnailImage thumbnailImage = thumbnailImageSaveHelper.saveThumbnailImage();
//		Member leader = memberSaveHelper.saveMember(thumbnailImage);
//		Member member = memberSaveHelper.saveMember(thumbnailImage);
//		StudyOnce studyOnce = studyOnceSaveHelper.saveStudyOnceWithTime(cafe, leader, start, end);
//		sut.tryJoin(member.getId(), studyOnce.getId());
//		LocalDateTime attendanceUpdateTime = start.plusMinutes(10).minusSeconds(1);
//		//when
//		assertThatThrownBy(
//			() -> sut.updateAttendance(leader.getId(), studyOnce.getId(), member.getId(), NO, attendanceUpdateTime))
//			.isInstanceOf(CafegoryException.class)
//			.hasMessage(STUDY_ONCE_EARLY_TAKE_ATTENDANCE.getErrorMessage());
//	}
//
//	@Test
//	@DisplayName("카공장은 카공 진행시간 절반이 지나기 전까지 출석체크를 할 수 있다.")
//	void leader_can_check_attendance_until_half_whole_study_time() {
//		//given
//		LocalDateTime start = NOW.plusHours(4);
//		LocalDateTime end = start.plusHours(4);
//		Cafe cafe = cafeSaveHelper.saveCafeWith24For7();
//		ThumbnailImage thumbnailImage = thumbnailImageSaveHelper.saveThumbnailImage();
//		Member leader = memberSaveHelper.saveMember(thumbnailImage);
//		Member member = memberSaveHelper.saveMember(thumbnailImage);
//		StudyOnce studyOnce = studyOnceSaveHelper.saveStudyOnceWithTime(cafe, leader, start, end);
//		sut.tryJoin(member.getId(), studyOnce.getId());
//		LocalDateTime attendanceUpdateTime = start.plusHours(2);
//		//when
//		sut.updateAttendance(leader.getId(), studyOnce.getId(), member.getId(), NO, attendanceUpdateTime);
//		//then
//		StudyMember studyMember = studyMemberRepository.findById(
//			new StudyMemberId(member.getId(), studyOnce.getId())
//		).get();
//		assertThat(studyMember.getAttendance()).isEqualTo(NO);
//	}
//
//	@Test
//	@DisplayName("카공 진행시간 절반이 지나면 출석체크를 할 수 없다.")
//	void leader_can_not_check_attendance_after_half_whole_study_time() {
//		//given
//		LocalDateTime start = NOW.plusHours(4);
//		LocalDateTime end = start.plusHours(4);
//		Cafe cafe = cafeSaveHelper.saveCafeWith24For7();
//		ThumbnailImage thumbnailImage = thumbnailImageSaveHelper.saveThumbnailImage();
//		Member leader = memberSaveHelper.saveMember(thumbnailImage);
//		Member member = memberSaveHelper.saveMember(thumbnailImage);
//		StudyOnce studyOnce = studyOnceSaveHelper.saveStudyOnceWithTime(cafe, leader, start, end);
//		sut.tryJoin(member.getId(), studyOnce.getId());
//		LocalDateTime attendanceUpdateTime = start.plusHours(2).plusSeconds(1);
//		//when
//		assertThatThrownBy(
//			() -> sut.updateAttendance(leader.getId(), studyOnce.getId(), member.getId(), NO, attendanceUpdateTime))
//			.isInstanceOf(CafegoryException.class)
//			.hasMessage(STUDY_ONCE_LATE_TAKE_ATTENDANCE.getErrorMessage());
//	}
//
//	@Test
//	@DisplayName("여러명 출석체크를 한다.")
//	void check_attendances() {
//		//given
//		LocalDateTime start = NOW.plusHours(4);
//		LocalDateTime end = start.plusHours(4);
//		Cafe cafe = cafeSaveHelper.saveCafeWith24For7();
//		ThumbnailImage thumbnailImage = thumbnailImageSaveHelper.saveThumbnailImage();
//		Member leader = memberSaveHelper.saveMember(thumbnailImage);
//		Member member1 = memberSaveHelper.saveMember(thumbnailImage);
//		Member member2 = memberSaveHelper.saveMember(thumbnailImage);
//		StudyOnce studyOnce = studyOnceSaveHelper.saveStudyOnceWithTime(cafe, leader, start, end);
//		sut.tryJoin(member1.getId(), studyOnce.getId());
//		sut.tryJoin(member2.getId(), studyOnce.getId());
//		LocalDateTime attendanceUpdateTime = start.plusMinutes(11);
//		UpdateAttendanceRequest request = new UpdateAttendanceRequest(
//			new StudyMemberStateRequest(member1.getId(), false),
//			new StudyMemberStateRequest(member2.getId(), false)
//		);
//		//when
//		UpdateAttendanceResponse response = sut.updateAttendances(leader.getId(), studyOnce.getId(),
//			request, attendanceUpdateTime);
//		//then
//		List<StudyMemberStateResponse> result = response.getStates();
//		assertAll(
//			() -> assertThat(result.get(0).isAttendance()).isEqualTo(false),
//			() -> assertThat(result.get(1).isAttendance()).isEqualTo(false)
//		);
//	}
//
//	@Test
//	@DisplayName("카공장만이 카공 장소를 변경할 수 있다.")
//	void only_leader_changes_cafe() {
//		//given
//		LocalDateTime start = NOW.plusHours(4);
//		LocalDateTime end = start.plusHours(4);
//		Cafe cafe1 = cafeSaveHelper.saveCafeWith24For7();
//		ThumbnailImage thumbnailImage = thumbnailImageSaveHelper.saveThumbnailImage();
//		Member leader = memberSaveHelper.saveMember(thumbnailImage);
//		StudyOnce studyOnce = studyOnceSaveHelper.saveStudyOnceWithTime(cafe1, leader, start, end);
//		Cafe cafe2 = cafeSaveHelper.saveCafeWith24For7();
//		//when
//		Long changedCafeId = sut.changeCafe(leader.getId(), studyOnce.getId(), cafe2.getId());
//		//then
//		assertThat(changedCafeId).isEqualTo(cafe2.getId());
//	}
//
//	@Test
//	@DisplayName("카공장만이 카공을 수정할 수 있다.")
//	void only_leader_updates_study_details() {
//		//given
//		LocalDateTime start = NOW.plusHours(4);
//		LocalDateTime end = start.plusHours(4);
//		Cafe cafe1 = cafeSaveHelper.saveCafeWith24For7();
//		ThumbnailImage thumbnailImage = thumbnailImageSaveHelper.saveThumbnailImage();
//		Member leader = memberSaveHelper.saveMember(thumbnailImage);
//		StudyOnce studyOnce = studyOnceSaveHelper.saveStudyOnceWithTime(cafe1, leader, start, end);
//		Cafe cafe2 = cafeSaveHelper.saveCafeWith24For7();
//		StudyOnceUpdateRequest request = new StudyOnceUpdateRequest(cafe2.getId(), "변경된카공이름", start.plusHours(5),
//			start.plusHours(6), 5, false, "오픈채팅방 링크");
//		//then
//		assertDoesNotThrow(() -> sut.updateStudyOnce(leader.getId(), studyOnce.getId(), request, LocalDateTime.now()));
//
//	}
//
//	@Test
//	@DisplayName("카공장이 아니라면 카공을 수정할 수 없다.")
//	void non_leader_can_not_update_study_details() {
//		//given
//		LocalDateTime start = NOW.plusHours(4);
//		LocalDateTime end = start.plusHours(4);
//		Cafe cafe1 = cafeSaveHelper.saveCafeWith24For7();
//		ThumbnailImage thumbnailImage = thumbnailImageSaveHelper.saveThumbnailImage();
//		Member leader = memberSaveHelper.saveMember(thumbnailImage);
//		Member member = memberSaveHelper.saveMember(thumbnailImage);
//		StudyOnce studyOnce = studyOnceSaveHelper.saveStudyOnceWithTime(cafe1, leader, start, end);
//		Cafe cafe2 = cafeSaveHelper.saveCafeWith24For7();
//		StudyOnceUpdateRequest request = new StudyOnceUpdateRequest(cafe2.getId(), "변경된카공이름", start.plusHours(5),
//			start.plusHours(6), 5, false, "오픈채팅방 링크");
//		//then
//		assertThatThrownBy(() -> sut.updateStudyOnce(member.getId(), studyOnce.getId(), request, LocalDateTime.now()))
//			.isInstanceOf(CafegoryException.class)
//			.hasMessage(STUDY_ONCE_LEADER_PERMISSION_DENIED.getErrorMessage());
//	}
//
//	@ParameterizedTest
//	@MethodSource("provideStartAndEndDateTime3")
//	@DisplayName("카공 시간 변경시 카공 시간은 카페 영업시간내에 포함되어야 한다.")
//	void study_time_must_be_within_cafe_business_hours(LocalDateTime start, LocalDateTime end) {
//		//given
//		Cafe cafe = cafeSaveHelper.saveCafeWith7daysFrom9To21();
//		ThumbnailImage thumbnailImage = thumbnailImageSaveHelper.saveThumbnailImage();
//		Member leader = memberSaveHelper.saveMember(thumbnailImage);
//		StudyOnce studyOnce = studyOnceSaveHelper.saveStudyOnceWithTime(cafe, leader,
//			LocalDateTime.of(2999, 1, 1, 9, 0),
//			LocalDateTime.of(2999, 1, 1, 10, 0));
//		StudyOnceUpdateRequest request = new StudyOnceUpdateRequest(cafe.getId(), null, start, end, 5,
//			true, null);
//		//then
//		assertThatThrownBy(
//			() -> sut.updateStudyOnce(leader.getId(), studyOnce.getId(),
//				request, LocalDateTime.of(2999, 1, 1, 8, 0)))
//			.isInstanceOf(CafegoryException.class)
//			.hasMessage(STUDY_ONCE_CREATE_BETWEEN_CAFE_BUSINESS_HOURS.getErrorMessage());
//	}
//
//	static Stream<Arguments> provideStartAndEndDateTime3() {
//		return Stream.of(
//			Arguments.of(
//				LocalDateTime.of(2999, 1, 1, 8, 59, 59),
//				LocalDateTime.of(2999, 1, 1, 10, 0)
//			),
//			Arguments.of(
//				LocalDateTime.of(2999, 1, 1, 8, 0),
//				LocalDateTime.of(2999, 1, 1, 9, 0, 0)
//			),
//			Arguments.of(
//				LocalDateTime.of(2999, 1, 1, 20, 0),
//				LocalDateTime.of(2999, 1, 1, 21, 0, 1)
//			),
//			Arguments.of(
//				LocalDateTime.of(2999, 1, 1, 20, 59, 59),
//				LocalDateTime.of(2999, 1, 1, 22, 0)
//			)
//		);
//	}
//
//	@ParameterizedTest()
//	@MethodSource("provideStartAndEndDateTime4")
//	@DisplayName("카공 시간 변경시 카공 시간은 카페 영업시간내에 포함된다.")
//	void study_time_is_within_cafe_business_hours(LocalDateTime start, LocalDateTime end) {
//		//given
//		Cafe cafe = cafeSaveHelper.saveCafeWith7daysFrom9To21();
//		ThumbnailImage thumbnailImage = thumbnailImageSaveHelper.saveThumbnailImage();
//		Member leader = memberSaveHelper.saveMember(thumbnailImage);
//		StudyOnce studyOnce = studyOnceSaveHelper.saveStudyOnceWithTime(cafe, leader,
//			LocalDateTime.of(2999, 1, 1, 9, 0),
//			LocalDateTime.of(2999, 1, 1, 10, 0));
//		StudyOnceUpdateRequest request = new StudyOnceUpdateRequest(cafe.getId(), null, start, end, 5,
//			true, null);
//		//then
//		assertDoesNotThrow(
//			() -> sut.updateStudyOnce(leader.getId(), studyOnce.getId(),
//				request, LocalDateTime.of(2999, 1, 1, 8, 0)));
//	}
//
//	static Stream<Arguments> provideStartAndEndDateTime4() {
//		return Stream.of(
//			Arguments.of(
//				LocalDateTime.of(2999, 1, 1, 9, 0),
//				LocalDateTime.of(2999, 1, 1, 10, 0)
//			),
//			Arguments.of(
//				LocalDateTime.of(2999, 1, 1, 20, 0),
//				LocalDateTime.of(2999, 1, 1, 21, 0)
//			)
//		);
//	}
//
//	@Test
//	@DisplayName("참여자가 존재하는 경우, 카공을 수정할때 카공 장소와 최대 참여 인원 수만 수정된다.")
//	void update_location_and_count_with_participants() {
//		//given
//		LocalDateTime start = NOW.plusHours(4);
//		Cafe cafe1 = cafeSaveHelper.saveCafe();
//		ThumbnailImage thumbnailImage = thumbnailImageSaveHelper.saveThumbnailImage();
//		Member leader = memberSaveHelper.saveMember(thumbnailImage);
//		StudyOnce studyOnce = studyOnceSaveHelper.saveStudyOnce(cafe1, leader);
//		Member member = memberSaveHelper.saveMember(thumbnailImage);
//		long cafeId2 = cafeSaveHelper.saveCafe().getId();
//		sut.tryJoin(member.getId(), studyOnce.getId());
//		StudyOnceUpdateRequest request = new StudyOnceUpdateRequest(cafeId2, "변경된카공이름", start.plusHours(5),
//			start.plusHours(6), 5, false, "오픈채팅방 링크");
//		//when
//		sut.updateStudyOncePartially(leader.getId(), studyOnce.getId(), request);
//		//then
//		StudyOnce result = studyOnceRepository.findById(studyOnce.getId()).get();
//		assertAll(
//			() -> assertThat(result.getName()).isEqualTo(studyOnce.getName()),
//			() -> assertThat(result.getCafe().getId()).isEqualTo(request.getCafeId()),
//			() -> assertThat(result.getStartDateTime()).isEqualTo(studyOnce.getStartDateTime()),
//			() -> assertThat(result.getEndDateTime()).isEqualTo(studyOnce.getEndDateTime()),
//			() -> assertThat(result.getMaxMemberCount()).isEqualTo(request.getMaxMemberCount()),
//			() -> assertThat(result.isAbleToTalk()).isEqualTo(true),
//			() -> assertThat(result.getOpenChatUrl()).isEqualTo(studyOnce.getOpenChatUrl())
//		);
//	}
//
//	@Test
//	@DisplayName("카공장이 아니라면 카공을 수정할 수 없다.")
//	void non_leader_can_not_update_study_details_partially() {
//		//given
//		Cafe cafe1 = cafeSaveHelper.saveCafe();
//		ThumbnailImage thumbnailImage = thumbnailImageSaveHelper.saveThumbnailImage();
//		Member leader = memberSaveHelper.saveMember(thumbnailImage);
//		Member member = memberSaveHelper.saveMember(thumbnailImage);
//		StudyOnce studyOnce = studyOnceSaveHelper.saveStudyOnce(cafe1, leader);
//		StudyOnceUpdateRequest request = new StudyOnceUpdateRequest(cafe1.getId(), null, null,
//			null, 5, true, null);
//		//then
//		assertThatThrownBy(
//			() -> sut.updateStudyOncePartially(member.getId(), studyOnce.getId(), request))
//			.isInstanceOf(CafegoryException.class)
//			.hasMessage(STUDY_ONCE_LEADER_PERMISSION_DENIED.getErrorMessage());
//	}

