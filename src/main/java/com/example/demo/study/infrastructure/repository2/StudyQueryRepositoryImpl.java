package com.example.demo.study.infrastructure.repository2;

import static com.example.demo.exception.ExceptionType.*;

import com.example.demo.study.domain.ViewCount;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import org.springframework.stereotype.Repository;

import com.example.demo.exception.CafegoryException;
import com.example.demo.study.domain.Study;
import com.example.demo.study.infrastructure.CafeStudyRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class StudyQueryRepositoryImpl implements StudyQueryRepository2 {

	private final CafeStudyRepository studyJpaRepository;

	@Override
	public Study findById(Long studyId) {
		return studyJpaRepository.findById(studyId)
			.orElseThrow(() -> new CafegoryException(CAFE_STUDY_NOT_FOUND))
			.toStudy();
	}

	@Override
	public Optional<Study> findWithMember(Long studyId) {
		return studyJpaRepository.findWithMember(studyId).map(CafeStudyEntity::toStudy);
	}

	@Override
	public Optional<ViewCount> findViewCountBy(Long studyId) {
		return studyJpaRepository.findById(studyId).map(CafeStudyEntity::toViewCount);
	}

	@Override
	public List<Study> findUpcomingsWithMemberBy(List<Long> studyIds, LocalDateTime now) {
		return studyJpaRepository.findUpcomingsWithMemberBy(studyIds, now).stream()
				.map(CafeStudyEntity::toStudy)
				.collect(Collectors.toList());
	}
}
