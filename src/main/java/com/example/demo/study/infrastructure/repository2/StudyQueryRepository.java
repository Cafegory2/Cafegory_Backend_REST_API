package com.example.demo.study.infrastructure.repository2;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.demo.auth.dto.SliceResponse;
import com.example.demo.study.domain.Study;
import com.example.demo.study.domain.StudyId;
import com.example.demo.study.domain.ViewCount;
import com.example.demo.study.infrastructure.CafeStudySearchListRequest;
import com.example.demo.study.infrastructure.CafeStudySearchListResponse;

public interface StudyQueryRepository {

	Study findById(StudyId studyId);

	Optional<Study> findWithMember(StudyId studyId);

	Optional<ViewCount> findViewCountBy(StudyId studyId);

	List<Study> findUpcomingsWithMemberBy(List<StudyId> studyIds, LocalDateTime now);

	SliceResponse<CafeStudySearchListResponse> findCafeStudies(CafeStudySearchListRequest request);
}
