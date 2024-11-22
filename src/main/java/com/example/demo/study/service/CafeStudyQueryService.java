package com.example.demo.study.service;

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

	public SliceResponse<CafeStudySearchListResponse> searchCafeStudiesByDynamicFilter(
		CafeStudySearchListRequest request
	) {
		SliceResponse<CafeStudyEntity> response = studyReader.searchCafeStudies(request);
		return response.map(CafeStudySearchListResponse::from);
	}

	public CafeStudyDetailResponse getCafeStudyDetail(Long cafeStudyId) {
		// 사용 안할거임
		CafeStudyEntity cafeStudy = studyReader.readStudyEntity(cafeStudyId);

		Study study = studyReader.read(cafeStudyId);
		ViewCount viewCount = studyReader.readViewCountBy(cafeStudyId);
		ParticipantCount participantCount = studyMemberReader.readParticipantCountBy(cafeStudyId);

		return CafeStudyDetailResponse.of(cafeStudy, study, viewCount, participantCount);
	}
}
