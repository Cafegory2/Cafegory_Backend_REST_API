package com.example.demo.study.infrastructure.repository2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.demo.study.domain.CafeStudyTagType;
import com.example.demo.study.domain.StudyTag;
import com.example.demo.study.infrastructure.CafeStudyTagEntity;
import com.example.demo.study.infrastructure.CafeStudyTagRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StudyTagRepository2Impl implements StudyTagRepository2 {

	private final CafeStudyTagRepository cafeStudyTagJpaRepository;

	@Override
	public List<StudyTag> findByTags(List<CafeStudyTagType> tags) {
		return cafeStudyTagJpaRepository.findByTags(tags).stream()
			.map(CafeStudyTagEntity::toStudyTag)
			.collect(Collectors.toList());
	}

	@Override
	public List<Long> countByTags(List<CafeStudyTagType> tags) {
		return cafeStudyTagJpaRepository.countByTags(tags);
	}

	public List<CafeStudyTagEntity> findEntityByTags(List<CafeStudyTagType> tags) {
		return cafeStudyTagJpaRepository.findByTags(tags);
	}
}
