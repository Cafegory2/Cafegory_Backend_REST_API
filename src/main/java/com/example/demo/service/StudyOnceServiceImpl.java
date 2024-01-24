package com.example.demo.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Address;
import com.example.demo.domain.CafeImpl;
import com.example.demo.domain.StudyOnceImpl;
import com.example.demo.dto.PagedResponse;
import com.example.demo.dto.StudyOnceCreateRequest;
import com.example.demo.dto.StudyOnceSearchRequest;
import com.example.demo.dto.StudyOnceSearchResponse;
import com.example.demo.dto.UpdateAttendanceResponse;
import com.example.demo.repository.CafeRepositoryWrapper;
import com.example.demo.repository.StudyOnceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudyOnceServiceImpl implements StudyOnceService {
	private final CafeRepositoryWrapper cafeRepositoryWrapper = id -> {
		CafeImpl cafe = CafeImpl.builder()
			.id(id)
			.address(new Address())
			.isOpen(true)
			.phone("010-1234-5678")
			.name("테스트 카페")
			.snsDetails(Collections.emptyList())
			.businessHours(Collections.emptyList())
			.avgReviewRate(4.5)
			.maxAllowableStay(1)
			.build();
		return Optional.of(cafe);
	};
	private final StudyOnceRepository studyOnceRepository;

	@Override
	public void tryJoin(String memberIdThatExpectedToJoin, String studyId) {

	}

	@Override
	public void tryQuit(String memberIdThatExpectedToQuit, String studyId) {

	}

	@Override
	public void tryCancel(String memberIdThatExpectedToCancel, String studyId) {

	}

	@Override
	public PagedResponse<StudyOnceSearchResponse> searchStudy(StudyOnceSearchRequest studyOnceSearchRequest) {
		return null;
	}

	@Override
	public List<UpdateAttendanceResponse> updateAttendance(String leaderId, String memberId, boolean attendance) {
		return null;
	}

	@Override
	public StudyOnceSearchResponse createStudy(String leaderId, StudyOnceCreateRequest studyOnceCreateRequest) {
		CafeImpl cafe = cafeRepositoryWrapper.findById(studyOnceCreateRequest.getCafeId()).orElseThrow();
		StudyOnceImpl studyOnce = makeNewStudyOnce(studyOnceCreateRequest, cafe);
		StudyOnceImpl saved = studyOnceRepository.save(studyOnce);
		boolean canJoin = true;
		return makeStudyOnceSearchRespone(saved, canJoin);
	}

	private static StudyOnceImpl makeNewStudyOnce(StudyOnceCreateRequest studyOnceCreateRequest, CafeImpl cafe) {
		return StudyOnceImpl.builder()
			.name(studyOnceCreateRequest.getName())
			.startDateTime(studyOnceCreateRequest.getStartDateTime())
			.endDateTime(studyOnceCreateRequest.getEndDateTime())
			.maxMemberCount(studyOnceCreateRequest.getMaxMemberCount())
			.nowMemberCount(0)
			.isEnd(false)
			.canTalk(studyOnceCreateRequest.isCanTalk())
			.cafe(cafe)
			.build();

	}

	private static StudyOnceSearchResponse makeStudyOnceSearchRespone(StudyOnceImpl saved, boolean canJoin) {
		return StudyOnceSearchResponse.builder()
			.cafeId(saved.getCafe().getId())
			.id(saved.getId())
			.name(saved.getName())
			.startDateTime(saved.getStartDateTime())
			.endDateTime(saved.getEndDateTime())
			.maxMemberCount(saved.getMaxMemberCount())
			.nowMemberCount(saved.getNowMemberCount())
			.canTalk(saved.isCanTalk())
			.canJoin(canJoin)
			.isEnd(saved.isEnd())
			.build();
	}
}
