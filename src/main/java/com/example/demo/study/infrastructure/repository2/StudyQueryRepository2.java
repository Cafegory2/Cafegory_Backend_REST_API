package com.example.demo.study.infrastructure.repository2;

import com.example.demo.study.domain.Study;
import com.example.demo.study.domain.StudyId;
import com.example.demo.study.domain.ViewCount;
import com.example.demo.study.infrastructure.CafeStudySearchListRequest;
import com.example.demo.study.infrastructure.CafeStudySearchListResponse;
import com.example.demo.auth.dto.SliceResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudyQueryRepository2 {

	Study findById(StudyId studyId);

	Optional<Study> findWithMember(StudyId studyId);

	Optional<ViewCount> findViewCountBy(StudyId studyId);

	List<Study> findUpcomingsWithMemberBy(List<StudyId> studyIds, LocalDateTime now);

	SliceResponse<CafeStudySearchListResponse> findCafeStudies(CafeStudySearchListRequest request);
}
