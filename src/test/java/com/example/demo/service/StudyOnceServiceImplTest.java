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
		LocalDateTime start = LocalDateTime.now();
		LocalDateTime end = start.minusHours(3);
		long cafeId = 1L;
		long leaderId = 1L;
		StudyOnceCreateRequest studyOnceCreateRequest = new StudyOnceCreateRequest(cafeId, "테스트 카페", start, end, 4,
			true);
		StudyOnceSearchResponse result = studyOnceService.createStudy(leaderId, studyOnceCreateRequest);
		StudyOnceSearchResponse expected = makeExpectedStudyOnceCreateResult(cafeId, studyOnceCreateRequest, result);
		Assertions.assertEquals(expected, result);
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