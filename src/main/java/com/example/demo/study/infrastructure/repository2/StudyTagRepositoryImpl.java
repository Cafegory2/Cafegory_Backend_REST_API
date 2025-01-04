package com.example.demo.study.infrastructure.repository2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.demo.study.domain.CafeStudyTagType;
import com.example.demo.study.domain.StudyTagId;
import com.example.demo.study.infrastructure.StudyTagJpaRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StudyTagRepositoryImpl implements StudyTagRepository {

	private final StudyTagJpaRepository cafeStudyTagJpaRepository;

	@Override
	public List<StudyTagId> countByTags(List<CafeStudyTagType> tags) {
		return cafeStudyTagJpaRepository.countByTags(tags).stream()
			.map(StudyTagId::new)
			.collect(Collectors.toList());
	}
}
