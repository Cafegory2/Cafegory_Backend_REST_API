package com.example.demo.study.infrastructure.repository2;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.CafegoryException;
import com.example.demo.study.domain.Study;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.study.infrastructure.CafeStudyRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StudyRepositoryImpl implements StudyRepository2 {

	private final CafeStudyRepository studyJpaRepository;

	@Override
	@Transactional
	public Long save(Study study, Long memberId) {
		return studyJpaRepository.save(new CafeStudyEntity(study, memberId)).getId();
	}

	@Override
	@Transactional
	public void deleteWithCascade(Long studyId, Long memberId, LocalDateTime now) {
		studyJpaRepository.findById(studyId)
			.orElseThrow(() -> new CafegoryException(CAFE_STUDY_NOT_FOUND)).softDelete(now);
	}
}
