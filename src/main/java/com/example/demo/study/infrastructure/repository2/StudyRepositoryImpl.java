package com.example.demo.study.infrastructure.repository2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.cafe.infrastructure.CafeRepository;
import com.example.demo.exception.CafegoryException;
import com.example.demo.exception.ExceptionType;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.member.infrastructure.MemberRepository;
import com.example.demo.study.domain.Study;
import com.example.demo.study.infrastructure.CafeStudyCafeStudyTagEntity;
import com.example.demo.study.infrastructure.CafeStudyCafeStudyTagRepository;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.study.infrastructure.CafeStudyRepository;
import com.example.demo.study.infrastructure.CafeStudyTagEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StudyRepositoryImpl implements StudyRepository2 {

	private final CafeStudyRepository cafeStudyJpaRepository;

	private final CafeRepository cafeJpaRepository;
	private final MemberRepository memberJpaRepository;
	private final StudyTagRepositoryImpl studyTagRepositoryImpl;
	private final CafeStudyCafeStudyTagRepository studyStudyTagJpaRepository;

	public Study save(Study study) {
		CafeEntity cafeEntity = cafeJpaRepository.findById(study.getCafeId())
			.orElseThrow(() -> new CafegoryException(ExceptionType.CAFE_NOT_FOUND));
		MemberEntity memberEntity = memberJpaRepository.findById(study.getCoordinator().getId())
			.orElseThrow(() -> new CafegoryException(ExceptionType.MEMBER_NOT_FOUND));

		CafeStudyEntity studyEntity = cafeStudyJpaRepository.save(
			CafeStudyEntity.from(study, cafeEntity, memberEntity));

		List<CafeStudyTagEntity> tagEntities = studyTagRepositoryImpl.findEntityByTags(study.getTags());
		List<CafeStudyCafeStudyTagEntity> studyStudyTagEntities = studyStudyTagJpaRepository.saveAll(
			buildStudyTagEntities(studyEntity, tagEntities));
		studyEntity.addCafeStudyTags(studyStudyTagEntities);

		return studyEntity.toStudy();
	}

	private List<CafeStudyCafeStudyTagEntity> buildStudyTagEntities(
		CafeStudyEntity studyEntity, List<CafeStudyTagEntity> tagEntities
	) {
		return tagEntities.stream()
			.map(cafeStudyTag -> CafeStudyCafeStudyTagEntity.builder()
				.cafeStudy(studyEntity)
				.cafeStudyTag(cafeStudyTag)
				.build()
			)
			.collect(Collectors.toList());
	}
}
