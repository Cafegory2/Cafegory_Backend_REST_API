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

import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.config.ServiceTest;
import com.example.demo.exception.CafegoryException;
import com.example.demo.helper.CafeSaveHelper;
import com.example.demo.helper.CafeStudySaveHelper;
import com.example.demo.helper.MemberSaveHelper;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.study.domain.Coordinator;
import com.example.demo.study.domain.MemberComms;
import com.example.demo.study.domain.Schedule;
import com.example.demo.study.domain.Study;
import com.example.demo.study.infrastructure.StudyMemberRepository;
import com.example.demo.study.presentation.CafeStudyCreateRequest;
import com.example.demo.study.service.CafeStudyService;
import com.example.demo.util.TimeUtil;

class CafeStudyServiceTest extends ServiceTest {

	@Autowired
	private CafeStudyService sut;

	@Autowired
	private StudyMemberRepository studyMemberRepository;

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
		CafeEntity cafe = cafeSaveHelper.saveCafeWith24For7();
		CafeStudyCreateRequest cafeStudyCreateRequest = makeCafeStudyCreateRequest(start, end, cafe.getId());
		//then
		assertDoesNotThrow(() ->
			sut.createStudy(leader.getId(), now, cafeStudyCreateRequest.toStudy()));
	}

	@Test
	@DisplayName("카공 시작은 현재 시간으로부터 최소 1시간 이후여야 한다.")
	void study_starts_1hours_after_now() {
		//given
		MemberEntity coordinator = memberSaveHelper.saveMember();
		LocalDateTime start = timeUtil.now().plusHours(1);
		LocalDateTime end = start.plusHours(1);
		CafeEntity cafe = cafeSaveHelper.saveCafeWith24For7();
		CafeStudyCreateRequest cafeStudyCreateRequest = makeCafeStudyCreateRequest(start, end, cafe.getId());
		//then
		assertDoesNotThrow(
			() -> sut.createStudy(coordinator.getId(), timeUtil.now(), cafeStudyCreateRequest.toStudy()));
	}

	@Test
	@DisplayName("카공 시작은 현재 시간으로부터 1시간 이전일 수 없다.")
	void study_starts_1hours_before_now() {
		//given
		MemberEntity coordinator = memberSaveHelper.saveMember();
		LocalDateTime start = timeUtil.now().plusHours(1).minusMinutes(1);
		LocalDateTime end = start.plusHours(1);
		CafeEntity cafe = cafeSaveHelper.saveCafeWith24For7();
		CafeStudyCreateRequest cafeStudyCreateRequest = makeCafeStudyCreateRequest(start, end, cafe.getId());
		//then
		assertThatThrownBy(
			() -> sut.createStudy(coordinator.getId(), timeUtil.now(), cafeStudyCreateRequest.toStudy())).isInstanceOf(
			CafegoryException.class).hasMessage(STUDY_ONCE_WRONG_START_TIME.getErrorMessage());
	}

	@Test
	@DisplayName("카공 시작은 현재 날짜로부터 한달 이내여야 한다.")
	void study_start_date_before_one_month() {
		//given
		MemberEntity coordinator = memberSaveHelper.saveMember();
		LocalDateTime start = timeUtil.now().plusMonths(1);
		LocalDateTime end = start.plusHours(1);
		CafeEntity cafe = cafeSaveHelper.saveCafeWith24For7();
		CafeStudyCreateRequest cafeStudyCreateRequest = makeCafeStudyCreateRequest(start, end, cafe.getId());
		//then
		assertDoesNotThrow(
			() -> sut.createStudy(coordinator.getId(), timeUtil.now(), cafeStudyCreateRequest.toStudy()));
	}

	@Test
	@DisplayName("카공 시작은 현재 날짜로부터 한달 이내여야 한다.")
	void study_start_date_after_one_month() {
		//given
		MemberEntity coordinator = memberSaveHelper.saveMember();
		LocalDateTime start = timeUtil.now().plusMonths(1).plusDays(1);
		LocalDateTime end = start.plusHours(1);
		CafeEntity cafe = cafeSaveHelper.saveCafeWith24For7();
		CafeStudyCreateRequest cafeStudyCreateRequest = makeCafeStudyCreateRequest(start, end, cafe.getId());
		//then
		assertThatThrownBy(
			() -> sut.createStudy(coordinator.getId(), timeUtil.now(), cafeStudyCreateRequest.toStudy())).isInstanceOf(
			CafegoryException.class).hasMessage(CAFE_STUDY_WRONG_START_DATE.getErrorMessage());
	}

	@Test
	@DisplayName("카공 생성시 카공장은 참여인원에 포함된다.")
	void includes_leader_in_participants() {
		//given
		CafeEntity cafe = cafeSaveHelper.saveCafeWith7daysFrom9To21();
		Coordinator coordinator = createCoordinator(memberSaveHelper.saveMember("coordinator@gmail.com"));
		LocalDateTime now = timeUtil.localDateTime(2000, 1, 1, 10, 0, 0);
		Study study = creatStudy(cafe.getId(), coordinator, now.plusHours(2), now.plusHours(4));
		//when
		Study savedStudy = sut.createStudy(coordinator.getId(), now, study);
		//then
		int result = studyMemberRepository.countByCafeStudy_Id(savedStudy.getId());
		assertThat(result).isEqualTo(1);
	}

	private Coordinator createCoordinator(MemberEntity member) {
		return Coordinator.builder()
			.id(member.getId())
			.nickname(member.getNickname())
			.build();
	}

	private Study creatStudy(Long cafeId, Coordinator coordinator, LocalDateTime start, LocalDateTime end) {
		return Study.builder()
			.name("카페고리 스터디")
			.cafeId(cafeId)
			.coordinator(coordinator)
			.schedule(
				Schedule.builder()
					.startDateTime(start)
					.endDateTime(end)
					.build()
			)
			.memberComms(MemberComms.WELCOME)
			.maxParticipantCount(5)
			.introduction("자기소개 글")
			.build();
	}

	@ParameterizedTest()
	@MethodSource("provideStartAndEndDateTime1")
	@DisplayName("카페 영업시간 밖의 시간에 카공을 만들 수 없다.")
	void study_can_not_start_outside_cafe_business_hours(LocalDateTime start, LocalDateTime end) {
		//given
		LocalDateTime now = timeUtil.localDateTime(2000, 1, 1, 0, 0, 0);

		CafeEntity cafe = cafeSaveHelper.saveCafeWith7daysFrom9To21();
		MemberEntity leader = memberSaveHelper.saveMember();
		CafeStudyCreateRequest cafeStudyCreateRequest = makeCafeStudyCreateRequest(start, end, cafe.getId());
		//then
		assertThatThrownBy(() -> sut.createStudy(leader.getId(), now, cafeStudyCreateRequest.toStudy())).isInstanceOf(
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

		CafeEntity cafe = cafeSaveHelper.saveCafeWith7daysFrom9To21();
		MemberEntity leader = memberSaveHelper.saveMember();
		CafeStudyCreateRequest studyOnceCreateRequest = makeCafeStudyCreateRequest(start, end, cafe.getId());
		//then
		assertDoesNotThrow(() -> sut.createStudy(leader.getId(), now, studyOnceCreateRequest.toStudy()));
	}

	static Stream<Arguments> provideStartAndEndDateTime2() {
		return Stream.of(Arguments.of(LocalDateTime.of(2000, 1, 1, 9, 0), LocalDateTime.of(2000, 1, 1, 10, 0)),
			Arguments.of(LocalDateTime.of(2000, 1, 1, 20, 0), LocalDateTime.of(2000, 1, 1, 21, 0)));
	}

}
