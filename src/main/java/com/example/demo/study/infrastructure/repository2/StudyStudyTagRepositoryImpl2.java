package com.example.demo.study.infrastructure.repository2;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.study.domain.StudyStudyTagId;
import com.example.demo.study.domain.StudyTagId;
import org.springframework.stereotype.Repository;

import com.example.demo.study.domain.StudyId;
import com.example.demo.study.infrastructure.CafeStudyCafeStudyTagEntity;
import com.example.demo.study.infrastructure.CafeStudyCafeStudyTagRepository;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.study.infrastructure.CafeStudyTagEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StudyStudyTagRepositoryImpl2 implements StudyStudyTagRepository2 {

	private final CafeStudyCafeStudyTagRepository studyStudyTagRepository;

	@Override
	public List<StudyStudyTagId> saveAll(StudyId studyId, List<StudyTagId> studyTagIds) {
		List<CafeStudyCafeStudyTagEntity> studyStudyTags = studyStudyTagRepository.saveAll(
			buildCafeStudyTags(studyId, studyTagIds));

		return studyStudyTags.stream()
			.map(studyStudyTag -> new StudyStudyTagId((studyStudyTag.getId())))
			.collect(Collectors.toList());
	}

	public void remove(Long studyId, LocalDateTime now) {
		studyStudyTagRepository.findByCafeStudy_Id(studyId)
			.forEach(studyTag -> studyTag.softDelete(now));
	}

	@Override
	public void remove(StudyId studyId, LocalDateTime now) {
		studyStudyTagRepository.findByCafeStudy_Id(studyId.getId())
				.forEach(studyTag -> studyTag.softDelete(now));
	}

	private List<CafeStudyCafeStudyTagEntity> buildCafeStudyTags(StudyId studyId, List<StudyTagId> studyTagIds) {
		return studyTagIds.stream()
			.map(studyTagId -> CafeStudyCafeStudyTagEntity.builder()
				.cafeStudy(new CafeStudyEntity(studyId.getId()))
				.cafeStudyTag(new CafeStudyTagEntity(studyTagId.getId()))
				.build()
			)
			.collect(Collectors.toList());
	}
}
