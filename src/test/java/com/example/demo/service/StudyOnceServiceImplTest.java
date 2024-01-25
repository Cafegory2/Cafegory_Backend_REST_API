package com.example.demo.service;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dto.StudyOnceCreateRequest;
import com.example.demo.dto.StudyOnceSearchResponse;

@SpringBootTest
class StudyOnceServiceImplTest {
	@Autowired
	StudyOnceServiceImpl studyOnceService;

	@Test
	@DisplayName("정상 생성 테스트")
	void create() {
		LocalDateTime start = LocalDateTime.now().plusHours(3).plusMinutes(1);
		LocalDateTime end = start.plusHours(1);
		long cafeId = 1L;
		long leaderId = 1L;
		StudyOnceCreateRequest studyOnceCreateRequest = new StudyOnceCreateRequest(cafeId, "테스트 카페", start, end, 4,
			true);
		StudyOnceSearchResponse result = studyOnceService.createStudy(leaderId, studyOnceCreateRequest);
		StudyOnceSearchResponse expected = makeExpectedStudyOnceCreateResult(cafeId, studyOnceCreateRequest, result);
		Assertions.assertEquals(expected, result);
	}

	@Test
	@DisplayName("카공 시작 시간이 현재 시간 + 3시간 보다 이전인 경우 실패")
	void createFailByStartTime() {
		LocalDateTime start = LocalDateTime.now().plusHours(3).minusSeconds(1);
		LocalDateTime end = start.plusHours(3);
		long cafeId = 1L;
		long leaderId = 1L;
		StudyOnceCreateRequest studyOnceCreateRequest = new StudyOnceCreateRequest(cafeId, "테스트 카페", start, end, 4,
			true);
		Assertions.assertThrows(IllegalArgumentException.class,
			() -> studyOnceService.createStudy(leaderId, studyOnceCreateRequest));
	}

	@Test
	@DisplayName("카공 시작 시간과 종료시간 차이가 1시간 미만일 경우 실패")
	void createFailByStartTimeAndEndTimePeriodIsTooShort() {
		LocalDateTime start = LocalDateTime.now().minusSeconds(1);
		LocalDateTime end = start.plusMinutes(59);
		long cafeId = 1L;
		long leaderId = 1L;
		StudyOnceCreateRequest studyOnceCreateRequest = new StudyOnceCreateRequest(cafeId, "테스트 카페", start, end, 4,
			true);
		Assertions.assertThrows(IllegalArgumentException.class,
			() -> studyOnceService.createStudy(leaderId, studyOnceCreateRequest));
	}

	@Test
	@DisplayName("카공 시작 시간과 종료시간 차이가 5시간 초과일 경우 실패")
	void createFailByStartTimeAndEndTimePeriodIsTooLong() {
		LocalDateTime start = LocalDateTime.now().minusSeconds(1);
		LocalDateTime end = start.plusHours(5).plusSeconds(1);
		long cafeId = 1L;
		long leaderId = 1L;
		StudyOnceCreateRequest studyOnceCreateRequest = new StudyOnceCreateRequest(cafeId, "테스트 카페", start, end, 4,
			true);
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