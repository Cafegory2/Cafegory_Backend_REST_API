package com.example.demo.study.infrastructure.repository2;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
import com.example.demo.study.infrastructure.StudyMemberRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StudyRepositoryImpl implements StudyRepository2 {

	private final CafeRepository cafeJpaRepository;

	private final CafeStudyRepository studyJpaRepository;
	private final StudyMemberRepository studyMemberJpaRepository;
	private final MemberRepository memberJpaRepository;

	private final StudyTagRepositoryImpl studyTagRepositoryImpl;
	private final CafeStudyCafeStudyTagRepository studyStudyTagJpaRepository;

	@Override
	@Transactional
	public Study saveWithCascade(Study study, Long memberId) {
		CafeEntity cafeEntity = cafeJpaRepository.findById(study.getCafeId())
			.orElseThrow(() -> new CafegoryException(ExceptionType.CAFE_NOT_FOUND));
		MemberEntity memberEntity = memberJpaRepository.findById(memberId)
			.orElseThrow(() -> new CafegoryException(ExceptionType.MEMBER_NOT_FOUND));

		CafeStudyEntity studyEntity = studyJpaRepository.save(
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

	@Override
	@Transactional
	public void deleteWithCascade(Long studyId, Long memberId, LocalDateTime now) {
		studyMemberJpaRepository.findByCafeStudy_IdAndMember_Id(studyId, memberId)
			.orElseThrow(() -> new CafegoryException(STUDY_MEMBER_NOT_FOUND))
			.softDelete(now);

		studyStudyTagJpaRepository.findByCafeStudy_Id(studyId)
			.forEach(studyTag -> studyTag.softDelete(now));

		studyJpaRepository.findById(studyId)
			.orElseThrow(() -> new CafegoryException(CAFE_STUDY_NOT_FOUND)).softDelete(now);
	}
}
