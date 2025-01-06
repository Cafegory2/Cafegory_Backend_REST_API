package com.example.demo.db.study.study;

import static com.example.demo.domain.exception.ExceptionType.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.demo.auth.dto.SliceResponse;
import com.example.demo.domain.exception.CafegoryException;
import com.example.demo.domain.study.domain.Study;
import com.example.demo.domain.study.domain.StudyId;
import com.example.demo.domain.study.repository.CafeStudySearchListRequest;
import com.example.demo.domain.study.repository.CafeStudySearchListResponse;
import com.example.demo.domain.study.repository.StudyQueryRepository;

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
	public int findViewCountBy(StudyId studyId) {
		return studyJpaRepository.findViewsById(studyId.getId());
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
