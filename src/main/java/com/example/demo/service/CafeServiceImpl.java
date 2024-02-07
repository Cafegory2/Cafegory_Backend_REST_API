package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.domain.BusinessHour;
import com.example.demo.domain.BusinessHourOpenChecker;
import com.example.demo.domain.CafeImpl;
import com.example.demo.domain.OpenChecker;
import com.example.demo.dto.BusinessHourResponse;
import com.example.demo.dto.CafeBasicInfoResponse;
import com.example.demo.dto.CafeResponse;
import com.example.demo.dto.CanMakeStudyOnceResponse;
import com.example.demo.dto.ReviewResponse;
import com.example.demo.dto.SnsResponse;
import com.example.demo.dto.StudyOnceForCafeResponse;
import com.example.demo.dto.WriterResponse;
import com.example.demo.repository.cafe.CafeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CafeServiceImpl implements CafeService {

	private final CafeRepository cafeRepository;
	private OpenChecker<BusinessHour> openChecker = new BusinessHourOpenChecker();

	@Override
	public CafeResponse findCafeById(Long cafeId) {

		CafeImpl findCafe = cafeRepository.findById(cafeId)
			.orElseThrow(() -> new IllegalArgumentException("없는 카페입니다."));

		return CafeResponse.builder()
			.basicInfo(
				new CafeBasicInfoResponse(
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
				)
			)
			.review(
				findCafe.getReviews().stream()
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
					.collect(Collectors.toList())
			)
			.meetings(
				findCafe.getStudyOnceGroup().stream()
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
					.collect(Collectors.toList())
			)
			.canMakeMeeting(
				List.of(new CanMakeStudyOnceResponse(), new CanMakeStudyOnceResponse())
			)
			.build();
	}
}
