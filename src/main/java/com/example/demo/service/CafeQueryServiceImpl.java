package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.BusinessHour;
import com.example.demo.domain.BusinessHourOpenChecker;
import com.example.demo.domain.CafeImpl;
import com.example.demo.domain.CafeSearchCondition;
import com.example.demo.domain.OpenChecker;
import com.example.demo.dto.BusinessHourResponse;
import com.example.demo.dto.CafeBasicInfoResponse;
import com.example.demo.dto.CafeResponse;
import com.example.demo.dto.CafeSearchRequest;
import com.example.demo.dto.CafeSearchResponse;
import com.example.demo.dto.CanMakeStudyOnceResponse;
import com.example.demo.dto.PagedResponse;
import com.example.demo.dto.ReviewResponse;
import com.example.demo.dto.SnsResponse;
import com.example.demo.dto.StudyOnceForCafeResponse;
import com.example.demo.dto.WriterResponse;
import com.example.demo.repository.cafe.CafeQueryRepository;
import com.example.demo.repository.cafe.CafeRepository;
import com.example.demo.util.PageRequestCustom;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CafeQueryServiceImpl implements CafeQueryService {

	private final CafeQueryRepository cafeQueryRepository;
	private final CafeRepository cafeRepository;
	private OpenChecker<BusinessHour> openChecker = new BusinessHourOpenChecker();

	@Override
	public PagedResponse<CafeSearchResponse> searchWithPagingByDynamicFilter(CafeSearchRequest request) {
		Pageable pageable = PageRequestCustom.of(request.getPage(), request.getSizePerPage());
		CafeSearchCondition cafeSearchCondition = createCafeSearchCondition(request);

		Page<CafeImpl> pagedCafes = cafeQueryRepository.findWithDynamicFilter(cafeSearchCondition,
			pageable);
		return createPagedResponse(pagedCafes, mapToResponseList(pagedCafes));
	}

	@Override
	public CafeResponse searchCafeById(Long cafeId) {
		CafeImpl findCafe = cafeRepository.findById(cafeId)
			.orElseThrow(() -> new IllegalArgumentException("없는 카페입니다."));

		return CafeResponse.builder()
			.basicInfo(
				produceCafeBasicInfoResponse(findCafe)
			)
			.review(
				mapToReviewResponseList(findCafe)
			)
			.meetings(
				mapToStudyOnceForCafeResponse(findCafe)
			)
			.canMakeMeeting(
				List.of(new CanMakeStudyOnceResponse(), new CanMakeStudyOnceResponse())
			)
			.build();
	}

	private PagedResponse<CafeSearchResponse> createPagedResponse(Page<CafeImpl> pagedCafes,
		List<CafeSearchResponse> cafeSearchResponses) {
		return PagedResponse.createWithFirstPageAsOne(
			pagedCafes.getNumber(),
			pagedCafes.getTotalPages(),
			pagedCafes.getNumberOfElements(),
			cafeSearchResponses
		);
	}

	private List<CafeSearchResponse> mapToResponseList(Page<CafeImpl> pagedCafes) {
		return pagedCafes.getContent().stream()
			.map(cafe ->
				new CafeSearchResponse(
					cafe.getId(),
					cafe.getName(),
					cafe.showFullAddress(),
					cafe.getBusinessHours().stream()
						.map(hour -> new BusinessHourResponse(hour.getDay(), hour.getStartTime().toString(),
							hour.getEndTime().toString()))
						.collect(Collectors.toList()),
					cafe.isOpen(openChecker),
					cafe.getSnsDetails().stream()
						.map(s -> new SnsResponse(s.getName(), s.getUrl()))
						.collect(Collectors.toList()),
					cafe.getPhone(),
					cafe.getMinBeveragePrice(),
					cafe.getMaxAllowableStay().getValue(),
					cafe.getAvgReviewRate()
				)
			)
			.collect(Collectors.toList());
	}

	private CafeSearchCondition createCafeSearchCondition(CafeSearchRequest request) {
		return new CafeSearchCondition.Builder(request.isCanStudy(),
			request.getArea())
			.maxTime(request.getMaxTime())
			.minMenuPrice(request.getMinBeveragePrice())
			.startTime(request.getStartTime())
			.endTime(request.getEndTime())
			.build();
	}

	private CafeBasicInfoResponse produceCafeBasicInfoResponse(CafeImpl findCafe) {
		return new CafeBasicInfoResponse(
			findCafe.getId(),
			findCafe.getName(),
			findCafe.showFullAddress(),
			findCafe.getBusinessHours().stream()
				.map(hour -> new BusinessHourResponse(hour.getDay(), hour.getStartTime().toString(),
					hour.getEndTime().toString()))
				.collect(Collectors.toList()),
			findCafe.isOpen(openChecker),
			findCafe.getSnsDetails().stream()
				.map(s -> new SnsResponse(s.getName(), s.getUrl()))
				.collect(Collectors.toList()),
			findCafe.getPhone(),
			findCafe.getMinBeveragePrice(),
			findCafe.getMaxAllowableStay().getValue(),
			findCafe.getAvgReviewRate()
		);
	}

	private List<ReviewResponse> mapToReviewResponseList(CafeImpl findCafe) {
		return findCafe.getReviews().stream()
			.map(review ->
				ReviewResponse.builder()
					.id(review.getId())
					.writer(
						new WriterResponse(review.getMember().getId(),
							review.getMember().getName(),
							review.getMember().getThumbnailImage().getThumbnailImage()
						))
					.rate(review.getRate())
					.content(review.getContent())
					.build()
			)
			.collect(Collectors.toList());
	}

	private static List<StudyOnceForCafeResponse> mapToStudyOnceForCafeResponse(CafeImpl findCafe) {
		return findCafe.getStudyOnceGroup().stream()
			.map(studyOnce ->
				StudyOnceForCafeResponse.builder()
					.cafeId(findCafe.getId())
					.id(studyOnce.getId())
					.name(studyOnce.getName())
					.startDateTime(studyOnce.getStartDateTime())
					.endDateTime(studyOnce.getEndDateTime())
					.maxMemberCount(studyOnce.getMaxMemberCount())
					.nowMemberCount(studyOnce.getNowMemberCount())
					.isEnd(studyOnce.isAbleToTalk())
					.build()
			)
			.collect(Collectors.toList());
	}
}
