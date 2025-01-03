package com.example.demo.study.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.cafe.domain.Cafe;
import com.example.demo.cafe.implement.CafeReader;
import com.example.demo.study.domain.ParticipantCount;
import com.example.demo.study.domain.Study;
import com.example.demo.study.domain.StudyId;
import com.example.demo.study.domain.ViewCount;
import com.example.demo.study.implement.StudyMemberReader;
import com.example.demo.study.implement.StudyReader;
import com.example.demo.study.infrastructure.CafeStudySearchListRequest;
import com.example.demo.study.infrastructure.CafeStudySearchListResponse;
import com.example.demo.study.presentation.CafeStudyDetailResponse;
import com.example.demo.auth.dto.SliceResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeStudyQueryService {

	private final StudyReader studyReader;
	private final StudyMemberReader studyMemberReader;
	private final CafeReader cafeReader;

	public SliceResponse<CafeStudySearchListResponse> searchCafeStudiesByDynamicFilter(
		CafeStudySearchListRequest request
	) {
		return studyReader.searchCafeStudies(request);
	}

	public CafeStudyDetailResponse getCafeStudyDetail(StudyId studyId) {
		Study study = studyReader.read(studyId);
		ViewCount viewCount = studyReader.readViewCountBy(studyId);
		ParticipantCount participantCount = studyMemberReader.readParticipantCountBy(studyId);
		Cafe cafe = cafeReader.read(study.getCafeId());

		return CafeStudyDetailResponse.of(cafe, study, viewCount, participantCount);
	}

	public Study getStudy(StudyId studyId) {
		return studyReader.read(studyId);
	}
}
