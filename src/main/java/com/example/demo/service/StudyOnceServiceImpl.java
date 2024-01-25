package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	public void tryJoin(long memberIdThatExpectedToJoin, long studyId) {

	}

	@Override
	public void tryQuit(long memberIdThatExpectedToQuit, long studyId) {

	}

	@Override
	public void tryCancel(long memberIdThatExpectedToCancel, long studyId) {

	}

	@Override
	public PagedResponse<StudyOnceSearchResponse> searchStudy(StudyOnceSearchRequest studyOnceSearchRequest) {
		int totalCount = Math.toIntExact(studyOnceRepository.count(studyOnceSearchRequest));
		int sizePerPage = studyOnceSearchRequest.getSizePerPage();
		int maxPage = calculateMaxPage(totalCount, sizePerPage);
		List<StudyOnceSearchResponse> searchResults = studyOnceRepository.findAllByStudyOnceSearchRequest(
				studyOnceSearchRequest)
			.stream()
			.map(studyOnce -> makeStudyOnceSearchResponse(studyOnce, studyOnce.canJoin(LocalDateTime.now())))
			.collect(Collectors.toList());
		return new PagedResponse<>(studyOnceSearchRequest.getPage(), maxPage, searchResults.size(), searchResults);
	}

	private int calculateMaxPage(int totalCount, int sizePerPage) {
		if (totalCount % sizePerPage == 0) {
			return totalCount / sizePerPage;
		}
		return totalCount / sizePerPage + 1;
	}

	@Override
	public StudyOnceSearchResponse searchByStudyId(long studyId) {
		StudyOnceImpl searched = studyOnceRepository.findById(studyId)
			.orElseThrow(() -> new IllegalArgumentException("해당 카공을 찾을 수 없습니다."));
		boolean canJoin = searched.canJoin(LocalDateTime.now());
		return makeStudyOnceSearchResponse(searched, canJoin);
	}

	@Override
	public List<UpdateAttendanceResponse> updateAttendance(long leaderId, long memberId, boolean attendance) {
		return null;
	}

	@Override
	public StudyOnceSearchResponse createStudy(long leaderId, StudyOnceCreateRequest studyOnceCreateRequest) {
		CafeImpl cafe = cafeRepositoryWrapper.findById(studyOnceCreateRequest.getCafeId()).orElseThrow();
		StudyOnceImpl studyOnce = makeNewStudyOnce(studyOnceCreateRequest, cafe);
		StudyOnceImpl saved = studyOnceRepository.save(studyOnce);
		boolean canJoin = true;
		return makeStudyOnceSearchResponse(saved, canJoin);
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

	private static StudyOnceSearchResponse makeStudyOnceSearchResponse(StudyOnceImpl saved, boolean canJoin) {
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
