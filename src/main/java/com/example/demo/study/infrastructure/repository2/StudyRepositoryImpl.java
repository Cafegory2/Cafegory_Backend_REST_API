package com.example.demo.study.infrastructure.repository2;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.member.domain.MemberId;
import com.example.demo.study.domain.Study;
import com.example.demo.study.domain.StudyId;
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
	public StudyId save(Study study, MemberId memberId) {
		return new StudyId(studyJpaRepository.save(new CafeStudyEntity(study, memberId.getId())).getId());
	}

	@Override
	public void deleteWithCascade(StudyId studyId, MemberId memberId, LocalDateTime now) {
		studyJpaRepository.findById(studyId.getId())
			.orElseThrow(() -> new IllegalArgumentException("해당 카공을 찾을 수 없습니다."))
			.softDelete(now);
	}
}
