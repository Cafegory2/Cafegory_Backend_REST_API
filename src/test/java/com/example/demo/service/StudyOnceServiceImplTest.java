package com.example.demo.service;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Address;
import com.example.demo.domain.CafeImpl;
import com.example.demo.domain.MemberImpl;
import com.example.demo.domain.ThumbnailImage;
import com.example.demo.dto.PagedResponse;
import com.example.demo.dto.StudyOnceCreateRequest;
import com.example.demo.dto.StudyOnceSearchRequest;
import com.example.demo.dto.StudyOnceSearchResponse;
import com.example.demo.exception.CafegoryException;

@SpringBootTest
@Transactional
class StudyOnceServiceImplTest {
	@Autowired
	StudyOnceServiceImpl studyOnceService;
	@Autowired
	private EntityManager em;

	private long initCafe() {
		Address address = new Address("테스트도 테스트시 테스트구 테스트동 ...", "테스트동");
		CafeImpl cafe = CafeImpl.builder()
			.address(address).build();
		em.persist(cafe);
		return cafe.getId();
	}

	private long initMember() {
		MemberImpl member = MemberImpl.builder()
			.name("테스트")
			.email("test@test.com")
			.thumbnailImage(ThumbnailImage.builder().thumbnailImage("testUrl").build())
			.build();
		em.persist(member);
		return member.getId();
	}

	@Test
	@DisplayName("정상 목록 조회 테스트")
	void searchStudyByDto() {
		//given
		LocalDateTime start = LocalDateTime.now().plusHours(4).plusMinutes(1);
		LocalDateTime end = start.plusHours(1);
		long cafeId = initCafe();
		long leaderId = initMember();
		StudyOnceCreateRequest studyOnceCreateRequest = makeStudyOnceCreateRequest(start, end, cafeId);

		//when
		StudyOnceSearchResponse expectedStudyOnceSearchResponse = studyOnceService.createStudy(leaderId,
			studyOnceCreateRequest);
		StudyOnceSearchRequest studyOnceSearchRequest = new StudyOnceSearchRequest("테스트동");
		PagedResponse<StudyOnceSearchResponse> pagedResponse = studyOnceService.searchStudy(studyOnceSearchRequest);

		//then
		List<StudyOnceSearchResponse> results = pagedResponse.getList();
		Assertions.assertThat(results).contains(expectedStudyOnceSearchResponse);
	}

	@Test
	@DisplayName("정상 조회 테스트")
	void searchByStudyId() {
		LocalDateTime start = LocalDateTime.now().plusHours(3).plusMinutes(1);
		LocalDateTime end = start.plusHours(1);
		long cafeId = initCafe();
		long leaderId = initMember();
		StudyOnceCreateRequest studyOnceCreateRequest = makeStudyOnceCreateRequest(start, end, cafeId);
		StudyOnceSearchResponse result = studyOnceService.createStudy(leaderId, studyOnceCreateRequest);
		StudyOnceSearchResponse studyOnceSearchResponse = studyOnceService.searchByStudyId(result.getId());
		Assertions.assertThat(studyOnceSearchResponse.getId()).isEqualTo(result.getId());
	}

	@Test
	@DisplayName("정상 생성 테스트")
	void create() {
		LocalDateTime start = LocalDateTime.now().plusHours(3).plusMinutes(1);
		LocalDateTime end = start.plusHours(1);
		long cafeId = initCafe();
		long leaderId = initMember();
		StudyOnceCreateRequest studyOnceCreateRequest = makeStudyOnceCreateRequest(start, end, cafeId);
		StudyOnceSearchResponse result = studyOnceService.createStudy(leaderId, studyOnceCreateRequest);
		StudyOnceSearchResponse expected = makeExpectedStudyOnceCreateResult(cafeId, studyOnceCreateRequest, result);
		Assertions.assertThat(result).isEqualTo(expected);
	}

	private static StudyOnceCreateRequest makeStudyOnceCreateRequest(LocalDateTime start, LocalDateTime end,
		long cafeId) {
		return new StudyOnceCreateRequest(cafeId, "테스트 카페", start, end, 4, true);
	}

	@Test
	@DisplayName("카공 시작 시간이 현재 시간 + 3시간 보다 이전인 경우 실패")
	void createFailByStartTime() {
		LocalDateTime start = LocalDateTime.now().plusHours(3).minusSeconds(1);
		LocalDateTime end = start.plusHours(3);
		long cafeId = initCafe();
		long leaderId = initMember();
		StudyOnceCreateRequest studyOnceCreateRequest = makeStudyOnceCreateRequest(start, end, cafeId);
		Assertions.assertThatThrownBy(() -> studyOnceService.createStudy(leaderId, studyOnceCreateRequest))
			.isInstanceOf(CafegoryException.class)
			.hasMessage(STUDY_ONCE_WRONG_START_TIME.getErrorMessage());
	}

	@Test
	@DisplayName("카공 시작 시간과 종료시간 차이가 1시간 미만일 경우 실패")
	void createFailByStartTimeAndEndTimePeriodIsTooShort() {
		LocalDateTime start = LocalDateTime.now().plusHours(3).plusMinutes(1);
		LocalDateTime end = start.plusMinutes(59).plusSeconds(59);
		long cafeId = initCafe();
		long leaderId = initMember();
		StudyOnceCreateRequest studyOnceCreateRequest = makeStudyOnceCreateRequest(start, end, cafeId);
		Assertions.assertThatThrownBy(() -> studyOnceService.createStudy(leaderId, studyOnceCreateRequest))
			.isInstanceOf(CafegoryException.class)
			.hasMessage(STUDY_ONCE_SHORT_DURATION.getErrorMessage());
	}

	@Test
	@DisplayName("카공 시작 시간과 종료시간 차이가 5시간 초과일 경우 실패")
	void createFailByStartTimeAndEndTimePeriodIsTooLong() {
		LocalDateTime start = LocalDateTime.now().plusHours(3).plusMinutes(1);
		LocalDateTime end = start.plusHours(5).plusSeconds(1);
		long cafeId = initCafe();
		long leaderId = initMember();
		StudyOnceCreateRequest studyOnceCreateRequest = makeStudyOnceCreateRequest(start, end, cafeId);
		Assertions.assertThatThrownBy(() -> studyOnceService.createStudy(leaderId, studyOnceCreateRequest))
			.isInstanceOf(CafegoryException.class)
			.hasMessage(STUDY_ONCE_LONG_DURATION.getErrorMessage());
	}

	private static StudyOnceSearchResponse makeExpectedStudyOnceCreateResult(long cafeId,
		StudyOnceCreateRequest studyOnceCreateRequest, StudyOnceSearchResponse result) {
		int nowMemberCount = 0;
		boolean canJoin = true;
		boolean isEnd = false;
		return StudyOnceSearchResponse.builder()
			.cafeId(cafeId)
			.id(result.getId())
			.name(studyOnceCreateRequest.getName())
			.startDateTime(studyOnceCreateRequest.getStartDateTime())
			.endDateTime(studyOnceCreateRequest.getEndDateTime())
			.maxMemberCount(studyOnceCreateRequest.getMaxMemberCount())
			.nowMemberCount(nowMemberCount)
			.canTalk(studyOnceCreateRequest.isCanTalk())
			.canJoin(canJoin)
			.isEnd(isEnd)
			.build();
	}

	@Test
	@DisplayName("이미 해당 시간에 카공장으로 참여중인 카공이 있는 경우 카공 생성 실패")
	void createFailByAlreadyStudyLeader() {
		LocalDateTime start = LocalDateTime.now().plusYears(1).plusHours(3).plusMinutes(1);
		LocalDateTime end = start.plusHours(1);
		long cafeId = initCafe();
		long leaderId = initMember();
		StudyOnceCreateRequest studyOnceCreateRequest = makeStudyOnceCreateRequest(start, end, cafeId);
		studyOnceService.createStudy(leaderId, studyOnceCreateRequest);
		// 오른쪽 끝에서 겹침
		LocalDateTime rightLimitStart = end;
		LocalDateTime rightLimitEnd = rightLimitStart.plusHours(1);
		StudyOnceCreateRequest needToFailStudyOnceCreateRequest = makeStudyOnceCreateRequest(rightLimitStart,
			rightLimitEnd, cafeId);
		Assertions.assertThatThrownBy(() -> studyOnceService.createStudy(leaderId, needToFailStudyOnceCreateRequest))
			.isInstanceOf(CafegoryException.class)
			.hasMessage(STUDY_ONCE_CONFLICT_TIME.getErrorMessage());
		// 왼쪽에서 겹침
		LocalDateTime leftLimitEnd = start;
		LocalDateTime leftLimitStart = leftLimitEnd.minusHours(3);
		StudyOnceCreateRequest needToFailStudyOnceCreateRequest2 = makeStudyOnceCreateRequest(leftLimitStart,
			leftLimitEnd, cafeId);
		Assertions.assertThatThrownBy(() -> studyOnceService.createStudy(leaderId, needToFailStudyOnceCreateRequest2))
			.isInstanceOf(CafegoryException.class)
			.hasMessage(STUDY_ONCE_CONFLICT_TIME.getErrorMessage());
	}

	@Test
	@DisplayName("이미 해당 시간에 참여중인 카공이 있는 경우 카공 생성 실패")
	void createFailByAlreadyStudyMember() {
		LocalDateTime start = LocalDateTime.now().plusYears(1).plusHours(3).plusMinutes(1);
		LocalDateTime end = start.plusHours(1);
		long cafeId = initCafe();
		long leaderId = initMember();
		long memberId = initMember();
		StudyOnceCreateRequest studyOnceCreateRequest = makeStudyOnceCreateRequest(start, end, cafeId);
		StudyOnceSearchResponse study = studyOnceService.createStudy(leaderId, studyOnceCreateRequest);
		studyOnceService.tryJoin(memberId, study.getId());
		// 오른쪽 끝에서 겹침
		LocalDateTime rightLimitStart = end;
		LocalDateTime rightLimitEnd = rightLimitStart.plusHours(1);
		StudyOnceCreateRequest needToFailStudyOnceCreateRequest = makeStudyOnceCreateRequest(rightLimitStart,
			rightLimitEnd, cafeId);
		Assertions.assertThatThrownBy(() -> studyOnceService.createStudy(memberId, needToFailStudyOnceCreateRequest))
			.isInstanceOf(CafegoryException.class)
			.hasMessage(STUDY_ONCE_CONFLICT_TIME.getErrorMessage());
		// 왼쪽에서 겹침
		LocalDateTime leftLimitEnd = start;
		LocalDateTime leftLimitStart = leftLimitEnd.minusHours(3);
		StudyOnceCreateRequest needToFailStudyOnceCreateRequest2 = makeStudyOnceCreateRequest(leftLimitStart,
			leftLimitEnd, cafeId);
		Assertions.assertThatThrownBy(() -> studyOnceService.createStudy(memberId, needToFailStudyOnceCreateRequest2))
			.isInstanceOf(CafegoryException.class)
			.hasMessage(STUDY_ONCE_CONFLICT_TIME.getErrorMessage());
	}

	@Test
	@DisplayName("1초 차이의 시간을 둔 카공은 생성 성공")
	void createTwo() {
		LocalDateTime start = LocalDateTime.now().plusYears(1).plusHours(3).plusMinutes(1);
		LocalDateTime end = start.plusHours(1);
		long cafeId = initCafe();
		long leaderId = initMember();
		StudyOnceCreateRequest studyOnceCreateRequest = makeStudyOnceCreateRequest(start, end, cafeId);
		studyOnceService.createStudy(leaderId, studyOnceCreateRequest);
		// 오른쪽 끝에서 1초 차이로 안 겹침
		LocalDateTime rightLimitStart = end.plusSeconds(1);
		LocalDateTime rightLimitEnd = rightLimitStart.plusHours(1);
		StudyOnceCreateRequest needToFailStudyOnceCreateRequest = makeStudyOnceCreateRequest(rightLimitStart,
			rightLimitEnd, cafeId);
		studyOnceService.createStudy(leaderId, needToFailStudyOnceCreateRequest);

		// 왼쪽 끝에서 1초 차이로 안 겹침
		LocalDateTime leftLimitEnd = start.minusSeconds(1);
		LocalDateTime leftLimitStart = leftLimitEnd.minusHours(3);
		needToFailStudyOnceCreateRequest = makeStudyOnceCreateRequest(leftLimitStart,
			leftLimitEnd, cafeId);
		studyOnceService.createStudy(leaderId, needToFailStudyOnceCreateRequest);
	}

	@Test
	@DisplayName("카공 참여 테스트")
	void tryJoin() {
		long firstMemberId = initMember();
		long secondMemberId = initMember();
		long cafeId = initCafe();
		StudyOnceCreateRequest studyOnceCreateRequest = makeStudyOnceCreateRequest(LocalDateTime.now().plusHours(4),
			LocalDateTime.now().plusHours(7), cafeId);
		StudyOnceSearchResponse study = studyOnceService.createStudy(firstMemberId, studyOnceCreateRequest);
		studyOnceService.tryJoin(secondMemberId, study.getId());
	}

	@Test
	@DisplayName("이미 참여중인 카공이라 참여 실패")
	void tryJoinFailCauseDuplicate() {
		long firstMemberId = initMember();
		long secondMemberId = initMember();
		long cafeId = initCafe();
		StudyOnceCreateRequest studyOnceCreateRequest = makeStudyOnceCreateRequest(LocalDateTime.now().plusHours(4),
			LocalDateTime.now().plusHours(7), cafeId);
		StudyOnceSearchResponse study = studyOnceService.createStudy(firstMemberId, studyOnceCreateRequest);
		//when
		studyOnceService.tryJoin(secondMemberId, study.getId());
		//then
		Assertions.assertThatThrownBy(() -> studyOnceService.tryJoin(secondMemberId, study.getId()))
			.isInstanceOf(CafegoryException.class)
			.hasMessage(STUDY_ONCE_DUPLICATE.getErrorMessage());
	}

	@ParameterizedTest
	@MethodSource("tryJoinFailCauseConflictParameters")
	@DisplayName("해당 시간에 이미 참여중인 카공이 있어서 참여 실패")
	void tryJoinFailCauseConflict(LocalDateTime start, LocalDateTime end, LocalDateTime conflictStart,
		LocalDateTime conflictEnd) {
		//given
		long firstMemberId = initMember();
		long secondMemberId = initMember();
		long thirdMemberId = initMember();
		long cafeId = initCafe();
		StudyOnceCreateRequest studyOnceCreateRequest = makeStudyOnceCreateRequest(start, end, cafeId);
		StudyOnceSearchResponse study = studyOnceService.createStudy(firstMemberId, studyOnceCreateRequest);
		studyOnceService.tryJoin(secondMemberId, study.getId());
		//when
		StudyOnceCreateRequest conflictStudyOnceCreateRequest = makeStudyOnceCreateRequest(conflictStart, conflictEnd,
			cafeId);
		StudyOnceSearchResponse conflictStudy = studyOnceService.createStudy(thirdMemberId,
			conflictStudyOnceCreateRequest);
		//then
		Assertions.assertThatThrownBy(() -> studyOnceService.tryJoin(secondMemberId, conflictStudy.getId()))
			.isInstanceOf(CafegoryException.class)
			.hasMessage(STUDY_ONCE_CONFLICT_TIME.getErrorMessage());
	}

	static Stream<Arguments> tryJoinFailCauseConflictParameters() {
		LocalDateTime start = LocalDateTime.now().plusHours(4);
		LocalDateTime end = start.plusHours(4);
		return Stream.of(Arguments.of(start, end, end.minusSeconds(1), end.minusSeconds(1).plusHours(4)),
			Arguments.of(start, end, start.plusSeconds(1), start.plusSeconds(1).plusHours(4)));
	}

	@Test
	@DisplayName("카공 참여 취소 테스트")
	void tryQuit() {
		LocalDateTime start = LocalDateTime.now().plusHours(4);
		LocalDateTime end = start.plusHours(4);
		long firstMemberId = initMember();
		long secondMemberId = initMember();
		long cafeId = initCafe();
		StudyOnceCreateRequest studyOnceCreateRequest = makeStudyOnceCreateRequest(start, end, cafeId);
		StudyOnceSearchResponse study = studyOnceService.createStudy(firstMemberId, studyOnceCreateRequest);
		studyOnceService.tryJoin(secondMemberId, study.getId());
		studyOnceService.tryQuit(secondMemberId, study.getId());
	}

	@Test
	@DisplayName("참여중인 카공이 아니라 참여 취소 실패")
	void tryQuitFailCauseNotJoin() {
		LocalDateTime start = LocalDateTime.now().plusHours(4);
		LocalDateTime end = start.plusHours(4);
		long firstMemberId = initMember();
		long secondMemberId = initMember();
		long cafeId = initCafe();
		StudyOnceCreateRequest studyOnceCreateRequest = makeStudyOnceCreateRequest(start, end, cafeId);
		StudyOnceSearchResponse study = studyOnceService.createStudy(firstMemberId, studyOnceCreateRequest);
		Assertions.assertThatThrownBy(() -> studyOnceService.tryQuit(secondMemberId, study.getId()))
			.isInstanceOf(CafegoryException.class)
			.hasMessage(STUDY_ONCE_TRY_QUIT_NOT_JOIN.getErrorMessage());
	}

	@Test
	@DisplayName("다른 참여자가 있어서 카공장이 참여 취소 실패")
	void tryQuitFailCauseNotOnlyLeader() {
		LocalDateTime start = LocalDateTime.now().plusHours(4);
		LocalDateTime end = start.plusHours(4);
		long firstMemberId = initMember();
		long secondMemberId = initMember();
		long cafeId = initCafe();
		StudyOnceCreateRequest studyOnceCreateRequest = makeStudyOnceCreateRequest(start, end, cafeId);
		StudyOnceSearchResponse study = studyOnceService.createStudy(firstMemberId, studyOnceCreateRequest);
		studyOnceService.tryJoin(secondMemberId, study.getId());
		Assertions.assertThatThrownBy(() -> studyOnceService.tryQuit(firstMemberId, study.getId()))
			.isInstanceOf(CafegoryException.class)
			.hasMessage(STUDY_ONCE_LEADER_QUIT_FAIL.getErrorMessage());
	}
}
