package com.example.demo.db.study.repository2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.demo.db.study.StudyTagJpaRepository;
import com.example.demo.domain.study.domain.CafeStudyTagType;
import com.example.demo.domain.study.domain.StudyTagId;

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
