package com.example.demo.study.infrastructure.repository2;

import static com.example.demo.exception.ExceptionType.*;

import org.springframework.stereotype.Repository;

import com.example.demo.exception.CafegoryException;
import com.example.demo.study.domain.Study;
import com.example.demo.study.infrastructure.CafeStudyRepository;

import lombok.RequiredArgsConstructor;

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
}
