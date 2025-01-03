package com.example.demo.study.infrastructure.repository2;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.study.domain.StudyTagId;
import org.springframework.stereotype.Repository;

import com.example.demo.study.domain.CafeStudyTagType;
import com.example.demo.study.domain.StudyId;
import com.example.demo.study.domain.StudyTag;
import com.example.demo.study.infrastructure.CafeStudyTagEntity;
import com.example.demo.study.infrastructure.CafeStudyTagRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StudyTagRepository2Impl implements StudyTagRepository2 {

	private final CafeStudyTagRepository cafeStudyTagJpaRepository;

	@Override
	public List<StudyTagId> countByTags(List<CafeStudyTagType> tags) {
		return cafeStudyTagJpaRepository.countByTags(tags).stream()
			.map(StudyTagId::new)
			.collect(Collectors.toList());
	}
}
