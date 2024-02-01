package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Address;
import com.example.demo.domain.CafeImpl;
import com.example.demo.dto.PagedResponse;
import com.example.demo.dto.StudyOnceCreateRequest;
import com.example.demo.dto.StudyOnceSearchRequest;
import com.example.demo.dto.StudyOnceSearchResponse;

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

	@Test
	@DisplayName("정상 목록 조회 테스트")
	void searchStudyByDto() {
		//given
		LocalDateTime start = LocalDateTime.now().plusHours(4).plusMinutes(1);
		LocalDateTime end = start.plusHours(1);
		long cafeId = initCafe();
		long leaderId = 1L;
		StudyOnceCreateRequest studyOnceCreateRequest = makeStudyOnceCreateRequest(start, end, cafeId);

		//when
		StudyOnceSearchResponse expectedStudyOnceSearchResponse = studyOnceService.createStudy(leaderId,
			studyOnceCreateRequest);
		StudyOnceSearchRequest studyOnceSearchRequest = new StudyOnceSearchRequest("테스트동");
		PagedResponse<StudyOnceSearchResponse> pagedResponse = studyOnceService.searchStudy(studyOnceSearchRequest);

		//then
		List<StudyOnceSearchResponse> results = pagedResponse.getList();
		org.assertj.core.api.Assertions.assertThat(results).contains(expectedStudyOnceSearchResponse);
	}

	@Test
	@DisplayName("정상 조회 테스트")
	void searchByStudyId() {
		LocalDateTime start = LocalDateTime.now().plusHours(3).plusMinutes(1);
		LocalDateTime end = start.plusHours(1);
		long cafeId = initCafe();
		long leaderId = 1L;
		StudyOnceCreateRequest studyOnceCreateRequest = makeStudyOnceCreateRequest(start, end, cafeId);
		StudyOnceSearchResponse result = studyOnceService.createStudy(leaderId, studyOnceCreateRequest);
		StudyOnceSearchResponse studyOnceSearchResponse = studyOnceService.searchByStudyId(result.getId());
		Assertions.assertEquals(result.getId(), studyOnceSearchResponse.getId());
	}

	@Test
	@DisplayName("정상 생성 테스트")
	void create() {
		LocalDateTime start = LocalDateTime.now().plusHours(3).plusMinutes(1);
		LocalDateTime end = start.plusHours(1);
		long cafeId = initCafe();
		long leaderId = 1L;
		StudyOnceCreateRequest studyOnceCreateRequest = makeStudyOnceCreateRequest(start, end, cafeId);
		StudyOnceSearchResponse result = studyOnceService.createStudy(leaderId, studyOnceCreateRequest);
		StudyOnceSearchResponse expected = makeExpectedStudyOnceCreateResult(cafeId, studyOnceCreateRequest, result);
		Assertions.assertEquals(expected, result);
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
		long leaderId = 1L;
		StudyOnceCreateRequest studyOnceCreateRequest = makeStudyOnceCreateRequest(start, end, cafeId);
		Assertions.assertThrows(IllegalArgumentException.class,
			() -> studyOnceService.createStudy(leaderId, studyOnceCreateRequest));
	}

	@Test
	@DisplayName("카공 시작 시간과 종료시간 차이가 1시간 미만일 경우 실패")
	void createFailByStartTimeAndEndTimePeriodIsTooShort() {
		LocalDateTime start = LocalDateTime.now().minusSeconds(1);
		LocalDateTime end = start.plusMinutes(59);
		long cafeId = initCafe();
		long leaderId = 1L;
		StudyOnceCreateRequest studyOnceCreateRequest = makeStudyOnceCreateRequest(start, end, cafeId);
		Assertions.assertThrows(IllegalArgumentException.class,
			() -> studyOnceService.createStudy(leaderId, studyOnceCreateRequest));
	}

	@Test
	@DisplayName("카공 시작 시간과 종료시간 차이가 5시간 초과일 경우 실패")
	void createFailByStartTimeAndEndTimePeriodIsTooLong() {
		LocalDateTime start = LocalDateTime.now().minusSeconds(1);
		LocalDateTime end = start.plusHours(5).plusSeconds(1);
		long cafeId = initCafe();
		long leaderId = 1L;
		StudyOnceCreateRequest studyOnceCreateRequest = makeStudyOnceCreateRequest(start, end, cafeId);
		Assertions.assertThrows(IllegalArgumentException.class,
			() -> studyOnceService.createStudy(leaderId, studyOnceCreateRequest));
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
}
