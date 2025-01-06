package com.example.demo.db.study.repository2;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.demo.auth.dto.SliceResponse;
import com.example.demo.db.study.CafeStudySearchListRequest;
import com.example.demo.db.study.CafeStudySearchListResponse;
import com.example.demo.domain.study.domain.Study;
import com.example.demo.domain.study.domain.StudyId;

public interface StudyQueryRepository {

	Study findById(StudyId studyId);

	Optional<Study> findWithMember(StudyId studyId);

	int findViewCountBy(StudyId studyId);

	List<Study> findUpcomingsWithMemberBy(List<StudyId> studyIds, LocalDateTime now);

	SliceResponse<CafeStudySearchListResponse> findCafeStudies(CafeStudySearchListRequest request);
}
