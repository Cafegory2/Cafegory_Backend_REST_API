package com.example.demo.study.infrastructure.repository2;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.exception.CafegoryException;
import com.example.demo.exception.ExceptionType;
import com.example.demo.study.domain.Study;
import com.example.demo.study.infrastructure.CafeStudyRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StudyQueryRepositoryImpl implements StudyQueryRepository2 {

	private final CafeStudyRepository cafeStudyJpaRepository;

	// TODO: test 확인하기
	@Override
	public Study findWithMember(Long studyId) {
		return cafeStudyJpaRepository.findWithMember(studyId)
			.orElseThrow(() -> new CafegoryException(ExceptionType.CAFE_STUDY_NOT_FOUND))
			.toStudy();
	}

	@Override
	public List<Study> findAllByCafeId(Long cafeId) {
		return List.of();
	}

	@Override
	public List<Study> findUpcomingsWithMemberBy(List<Long> studyIds, LocalDateTime now) {
		return List.of();
	}
}
