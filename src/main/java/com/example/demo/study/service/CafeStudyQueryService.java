package com.example.demo.study.service;

import com.example.demo.cafe.domain.Cafe;
import com.example.demo.cafe.implement.CafeReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.study.domain.ParticipantCount;
import com.example.demo.study.domain.Study;
import com.example.demo.study.domain.ViewCount;
import com.example.demo.study.implement.StudyMemberReader;
import com.example.demo.study.implement.StudyReader;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.study.infrastructure.CafeStudySearchListRequest;
import com.example.demo.study.infrastructure.CafeStudySearchListResponse;
import com.example.demo.study.presentation.CafeStudyDetailResponse;
import com.example.demo.trash.dto.SliceResponse;

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
		SliceResponse<CafeStudyEntity> response = studyReader.searchCafeStudies(request);
		return response.map(CafeStudySearchListResponse::from);
	}

	public CafeStudyDetailResponse getCafeStudyDetail(Long cafeStudyId) {
		Study study = studyReader.read(cafeStudyId);
		ViewCount viewCount = studyReader.readViewCountBy(cafeStudyId);
		ParticipantCount participantCount = studyMemberReader.readParticipantCountBy(cafeStudyId);
		Cafe cafe = cafeReader.read(study.getCafeId());

		return CafeStudyDetailResponse.of(cafe, study, viewCount, participantCount);
	}
}
