package com.example.demo.study.infrastructure.repository2;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
	public List<Long> saveAll(Long studyId, List<Long> studyTagIds) {
		List<CafeStudyCafeStudyTagEntity> savedTags = studyStudyTagRepository.saveAll(
			buildCafeStudyTags(studyId, studyTagIds));

		return savedTags.stream()
			.map(CafeStudyCafeStudyTagEntity::getId)
			.collect(Collectors.toList());
	}

	@Override
	public List<StudyId> saveAll2(StudyId studyId, List<StudyId> studyTagIds) {
		List<CafeStudyCafeStudyTagEntity> savedTags = studyStudyTagRepository.saveAll(
			buildCafeStudyTags2(studyId, studyTagIds));

		return savedTags.stream()
			.map(tag -> new StudyId((tag.getId())))
			.collect(Collectors.toList());
	}

	public void remove(Long studyId, LocalDateTime now) {
		studyStudyTagRepository.findByCafeStudy_Id(studyId)
			.forEach(studyTag -> studyTag.softDelete(now));
	}

	private List<CafeStudyCafeStudyTagEntity> buildCafeStudyTags(Long studyId, List<Long> studyTagIds) {
		return studyTagIds.stream()
			.map(studyTagId -> CafeStudyCafeStudyTagEntity.builder()
				.cafeStudy(new CafeStudyEntity(studyId))
				.cafeStudyTag(new CafeStudyTagEntity(studyTagId))
				.build()
			)
			.collect(Collectors.toList());
	}

	private List<CafeStudyCafeStudyTagEntity> buildCafeStudyTags2(StudyId studyId, List<StudyId> studyTagIds) {
		return studyTagIds.stream()
			.map(studyTagId -> CafeStudyCafeStudyTagEntity.builder()
				.cafeStudy(new CafeStudyEntity(studyId.getId()))
				.cafeStudyTag(new CafeStudyTagEntity(studyTagId.getId()))
				.build()
			)
			.collect(Collectors.toList());
	}
}
