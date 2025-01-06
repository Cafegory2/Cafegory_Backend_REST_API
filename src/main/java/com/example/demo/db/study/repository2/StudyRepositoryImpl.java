package com.example.demo.db.study.repository2;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

import com.example.demo.db.study.CafeStudyEntity;
import com.example.demo.db.study.StudyJpaRepository;
import com.example.demo.domain.cafe.domain.CafeId;
import com.example.demo.domain.member.domain.MemberId;
import com.example.demo.domain.study.domain.StudyContent;
import com.example.demo.domain.study.domain.StudyId;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StudyRepositoryImpl implements StudyRepository {

	private final StudyJpaRepository studyJpaRepository;

	@Override
	public StudyId save(StudyContent content, CafeId cafeId, MemberId memberId) {
		CafeStudyEntity studyEntity = studyJpaRepository.save(
			new CafeStudyEntity(content, cafeId.getId(), memberId.getId()));
		return new StudyId(studyEntity.getId());
	}

	@Override
	public void deleteWithCascade(StudyId studyId, MemberId memberId, LocalDateTime now) {
		studyJpaRepository.findById(studyId.getId())
			.orElseThrow(() -> new IllegalArgumentException("해당 카공을 찾을 수 없습니다."))
			.softDelete(now);
	}
}
