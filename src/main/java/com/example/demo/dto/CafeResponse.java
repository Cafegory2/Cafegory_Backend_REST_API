package com.example.demo.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CafeResponse {

	private final CafeBasicInfoResponse basicInfo;
	private final List<ReviewResponse> review;
	private final List<StudyOnceForCafeResponse> meetings;
	private final List<CanMakeStudyOnceResponse> canMakeMeeting;
}
