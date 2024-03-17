package com.example.demo.service;

import static com.example.demo.exception.ExceptionType.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.BusinessHour;
import com.example.demo.domain.BusinessHourOpenChecker;
import com.example.demo.domain.CafeImpl;
import com.example.demo.domain.CafeSearchCondition;
import com.example.demo.domain.OpenChecker;
import com.example.demo.dto.CafeResponse;
import com.example.demo.dto.CafeSearchRequest;
import com.example.demo.dto.CafeSearchResponse;
import com.example.demo.dto.PagedResponse;
import com.example.demo.exception.CafegoryException;
import com.example.demo.mapper.BusinessHourMapper;
import com.example.demo.mapper.CafeMapper;
import com.example.demo.mapper.ReviewMapper;
import com.example.demo.mapper.SnsDetailMapper;
import com.example.demo.mapper.StudyOnceMapper;
import com.example.demo.repository.MemberRepository;
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
	private final MemberRepository memberRepository;
	private OpenChecker<BusinessHour> openChecker = new BusinessHourOpenChecker();
	private BusinessHourMapper businessHourMapper = new BusinessHourMapper();
	private SnsDetailMapper snsDetailMapper = new SnsDetailMapper();
	private CafeMapper cafeMapper = new CafeMapper();
	private ReviewMapper reviewMapper = new ReviewMapper();
	private StudyOnceMapper studyOnceMapper = new StudyOnceMapper();

	@Override
	public PagedResponse<CafeSearchResponse> searchWithPagingByDynamicFilter(CafeSearchRequest request) {
		Pageable pageable = PageRequestCustom.of(request.getPage(), request.getSizePerPage());
		// CafeSearchCondition cafeSearchCondition = createCafeSearchCondition(request);
		CafeSearchCondition cafeSearchCondition = cafeMapper.toCafeSearchCondition(request);

		Page<CafeImpl> pagedCafes = cafeQueryRepository.findWithDynamicFilter(cafeSearchCondition,
			pageable);
		// return createPagedResponse(pagedCafes, mapToResponseList(pagedCafes));
		return createPagedResponse(pagedCafes,
			cafeMapper.toCafeSearchResponses(pagedCafes.getContent(), openChecker));
	}

	@Override
	public CafeResponse searchCafeById(Long cafeId) {
		CafeImpl findCafe = findCafeById(cafeId);
		// return produceCafeResponse(findCafe);
		return cafeMapper.toCafeResponse(
			findCafe,
			businessHourMapper.toBusinessHourResponses(findCafe.getBusinessHours()),
			snsDetailMapper.toSnsResponses(findCafe.getSnsDetails()),
			reviewMapper.toReviewResponses(findCafe.getReviews()),
			studyOnceMapper.toStudyOnceForCafeResponse(findCafe),
			openChecker
		);
	}

	private CafeImpl findCafeById(Long cafeId) {
		return cafeRepository.findById(cafeId)
			.orElseThrow(() -> new CafegoryException(CAFE_NOT_FOUND));
	}

	@Override
	public CafeResponse searchCafeForMemberByCafeId(Long cafeId, Long memberId) {
		CafeImpl findCafe = findCafeById(cafeId);
		if (!memberRepository.existsById(memberId)) {
			throw new CafegoryException(MEMBER_NOT_FOUND);
		}
		// return produceCafeResponse(findCafe);
		return cafeMapper.toCafeResponse(
			findCafe,
			businessHourMapper.toBusinessHourResponses(findCafe.getBusinessHours()),
			snsDetailMapper.toSnsResponses(findCafe.getSnsDetails()),
			reviewMapper.toReviewResponses(findCafe.getReviews()),
			studyOnceMapper.toStudyOnceForCafeResponse(findCafe),
			openChecker
		);
	}

	@Override
	public CafeResponse searchCafeForNotMemberByCafeId(Long cafeId) {
		CafeImpl findCafe = findCafeById(cafeId);
		// return produceCafeResponseWithEmptyInfo(findCafe);
		return cafeMapper.toCafeResponseWithEmptyInfo(
			findCafe,
			businessHourMapper.toBusinessHourResponses(findCafe.getBusinessHours()),
			snsDetailMapper.toSnsResponses(findCafe.getSnsDetails()),
			reviewMapper.toReviewResponses(findCafe.getReviews()),
			openChecker
		);
	}

	// private CafeResponse produceCafeResponseWithEmptyInfo(CafeImpl findCafe) {
	// 	return CafeResponse.builder()
	// 		.basicInfo(
	// 			cafeMapper.entityToCafeBasicInfoResponse(
	// 				findCafe,
	// 				businessHourMapper.entitiesToBusinessHourResponses(findCafe.getBusinessHours()),
	// 				snsDetailMapper.entitiesToSnsResponses(findCafe.getSnsDetails()),
	// 				openChecker
	// 			)
	// 		)
	// 		.review(
	// 			reviewMapper.entitiesToReviewResponses(findCafe.getReviews())
	// 			// mapToReviewResponseList(findCafe)
	// 		)
	// 		.meetings(
	// 			Collections.emptyList()
	// 		)
	// 		.canMakeMeeting(
	// 			Collections.emptyList()
	// 		)
	// 		.build();
	// }

	// private CafeResponse produceCafeResponse(CafeImpl findCafe) {
	// 	return CafeResponse.builder()
	// 		.basicInfo(
	// 			cafeMapper.entityToCafeBasicInfoResponse(
	// 				findCafe,
	// 				businessHourMapper.entitiesToBusinessHourResponses(findCafe.getBusinessHours()),
	// 				snsDetailMapper.entitiesToSnsResponses(findCafe.getSnsDetails()),
	// 				openChecker)
	// 		)
	// 		.review(
	// 			reviewMapper.entitiesToReviewResponses(findCafe.getReviews())
	// 		)
	// 		.meetings(
	// 			studyOnceMapper.cafeToStudyOnceForCafeResponse(findCafe)
	// 		)
	// 		.canMakeMeeting(
	// 			List.of(new CanMakeStudyOnceResponse(), new CanMakeStudyOnceResponse())
	// 		)
	// 		.build();
	// }

	private PagedResponse<CafeSearchResponse> createPagedResponse(Page<CafeImpl> pagedCafes,
		List<CafeSearchResponse> cafeSearchResponses) {
		return PagedResponse.createWithFirstPageAsOne(
			pagedCafes.getNumber(),
			pagedCafes.getTotalPages(),
			pagedCafes.getNumberOfElements(),
			cafeSearchResponses
		);
	}

	// private List<CafeSearchResponse> mapToResponseList(Page<CafeImpl> pagedCafes) {
	// 	return pagedCafes.getContent().stream()
	// 		.map(cafe ->
	// 			new CafeSearchResponse(
	// 				cafe.getId(),
	// 				cafe.getName(),
	// 				cafe.showFullAddress(),
	// 				businessHourMapper.entitiesToBusinessHourResponses(cafe.getBusinessHours()),
	// 				// cafe.getBusinessHours().stream()
	// 				// 	.map(hour -> new BusinessHourResponse(hour.getDay(), hour.getStartTime().toString(),
	// 				// 		hour.getEndTime().toString()))
	// 				// 	.collect(Collectors.toList()),
	// 				cafe.isOpen(openChecker),
	// 				snsDetailMapper.entitiesToSnsResponses(cafe.getSnsDetails()),
	// 				// cafe.getSnsDetails().stream()
	// 				// 	.map(s -> new SnsResponse(s.getName(), s.getUrl()))
	// 				// 	.collect(Collectors.toList()),
	// 				cafe.getPhone(),
	// 				cafe.getMinBeveragePrice(),
	// 				cafe.getMaxAllowableStay().getValue(),
	// 				cafe.getAvgReviewRate()
	// 			)
	// 		)
	// 		.collect(Collectors.toList());
	// }

	// private CafeSearchCondition createCafeSearchCondition(CafeSearchRequest request) {
	// 	return new CafeSearchCondition.Builder(request.isCanStudy(),
	// 		request.getArea())
	// 		.maxTime(request.getMaxTime())
	// 		.minMenuPrice(request.getMinBeveragePrice())
	// 		.startTime(request.getStartTime())
	// 		.endTime(request.getEndTime())
	// 		.build();
	// }

	// private CafeBasicInfoResponse produceCafeBasicInfoResponse(CafeImpl findCafe) {
	// 	return new CafeBasicInfoResponse(
	// 		findCafe.getId(),
	// 		findCafe.getName(),
	// 		findCafe.showFullAddress(),
	// 		findCafe.getBusinessHours().stream()
	// 			.map(hour -> new BusinessHourResponse(hour.getDay(), hour.getStartTime().toString(),
	// 				hour.getEndTime().toString()))
	// 			.collect(Collectors.toList()),
	// 		findCafe.isOpen(openChecker),
	// 		findCafe.getSnsDetails().stream()
	// 			.map(s -> new SnsResponse(s.getName(), s.getUrl()))
	// 			.collect(Collectors.toList()),
	// 		findCafe.getPhone(),
	// 		findCafe.getMinBeveragePrice(),
	// 		findCafe.getMaxAllowableStay().getValue(),
	// 		findCafe.getAvgReviewRate()
	// 	);
	// }

	// private List<ReviewResponse> mapToReviewResponseList(CafeImpl findCafe) {
	// 	return findCafe.getReviews().stream()
	// 		.map(review ->
	// 			ReviewResponse.builder()
	// 				.reviewId(review.getId())
	// 				.writer(
	// 					new WriterResponse(review.getMember().getId(),
	// 						review.getMember().getName(),
	// 						review.getMember().getThumbnailImage().getThumbnailImage()
	// 					))
	// 				.rate(review.getRate())
	// 				.content(review.getContent())
	// 				.build()
	// 		)
	// 		.collect(Collectors.toList());
	// }

	// private List<StudyOnceForCafeResponse> mapToStudyOnceForCafeResponse(CafeImpl findCafe) {
	// 	return findCafe.getStudyOnceGroup().stream()
	// 		.map(studyOnce ->
	// 			StudyOnceForCafeResponse.builder()
	// 				.cafeId(findCafe.getId())
	// 				.studyOnceId(studyOnce.getId())
	// 				.name(studyOnce.getName())
	// 				.startDateTime(studyOnce.getStartDateTime())
	// 				.endDateTime(studyOnce.getEndDateTime())
	// 				.maxMemberCount(studyOnce.getMaxMemberCount())
	// 				.nowMemberCount(studyOnce.getNowMemberCount())
	// 				.isEnd(studyOnce.isAbleToTalk())
	// 				.build()
	// 		)
	// 		.collect(Collectors.toList());
	// }
}
