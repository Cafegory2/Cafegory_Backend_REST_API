package com.example.demo.study.infrastructure.repository2;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.demo.auth.dto.SliceResponse;
import com.example.demo.exception.CafegoryException;
import com.example.demo.study.domain.Study;
import com.example.demo.study.domain.StudyId;
import com.example.demo.study.domain.ViewCount;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.study.infrastructure.CafeStudySearchListRequest;
import com.example.demo.study.infrastructure.CafeStudySearchListResponse;
import com.example.demo.study.infrastructure.StudyJpaRepository;
import com.example.demo.study.infrastructure.StudyQueryDslRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StudyQueryRepositoryImpl implements StudyQueryRepository {

	private final StudyJpaRepository studyJpaRepository;
	private final StudyQueryDslRepository studyQueryDslRepository;

	@Override
	public Study findById(StudyId studyId) {
		return studyJpaRepository.findById(studyId.getId())
			.orElseThrow(() -> new CafegoryException(CAFE_STUDY_NOT_FOUND))
			.toStudy();
	}

	@Override
	public Optional<Study> findWithMember(StudyId studyId) {
		return studyJpaRepository.findWithMember(studyId.getId()).map(CafeStudyEntity::toStudy);
	}

	@Override
	public Optional<ViewCount> findViewCountBy(StudyId studyId) {
		return studyJpaRepository.findById(studyId.getId()).map(CafeStudyEntity::toViewCount);
	}

	@Override
	public List<Study> findUpcomingsWithMemberBy(List<StudyId> studyIds, LocalDateTime now) {
		List<Long> ids = studyIds.stream()
			.map(StudyId::getId)
			.collect(Collectors.toList());

		return studyJpaRepository.findUpcomingsWithMemberBy(ids, now).stream()
			.map(CafeStudyEntity::toStudy)
			.collect(Collectors.toList());
	}

	@Override
	public SliceResponse<CafeStudySearchListResponse> findCafeStudies(CafeStudySearchListRequest request) {
		SliceResponse<CafeStudyEntity> response = studyQueryDslRepository.findCafeStudies(request);
		return response.map(CafeStudySearchListResponse::from);
	}
}
