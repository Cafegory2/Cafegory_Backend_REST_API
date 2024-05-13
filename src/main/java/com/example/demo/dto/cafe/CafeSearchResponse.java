package com.example.demo.dto.cafe;

import java.util.List;

import com.example.demo.dto.study.CanMakeStudyOnceResponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CafeSearchResponse {

	private final CafeSearchBasicInfoResponse basicInfo;
	private final long totalElementsOfReview;
	private final List<CafeSearchStudyOnceResponse> meetings;
	private final List<CanMakeStudyOnceResponse> canMakeMeeting;
}
