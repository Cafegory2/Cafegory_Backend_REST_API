package com.example.demo.study.implement;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.cafe.infrastructure.CafeRepository;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.member.infrastructure.MemberRepository;
import com.example.demo.study.domain.Study;
import com.example.demo.study.infrastructure.CafeStudyCafeStudyTagEntity;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.study.infrastructure.CafeStudyRepository;
import com.example.demo.study.infrastructure.CafeStudyTagEntity;
import com.example.demo.study.infrastructure.StudyPeriod;
import com.example.demo.study.infrastructure.repository2.StudyQueryRepository2;
import com.example.demo.study.infrastructure.repository2.StudyRepository2;
import com.example.demo.study.infrastructure.repository2.StudyStudyTagRepository2;
import com.example.demo.study.infrastructure.repository2.StudyTagRepository2;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StudyEditor {

	private final StudyRepository2 studyRepository2;
	private final StudyQueryRepository2 studyQueryRepository2;

	private final MemberRepository memberRepository;
	private final CafeRepository cafeRepository;
	private final CafeStudyRepository cafeStudyRepository;

	private final StudyTagRepository2 studyTagRepository2;
	private final StudyStudyTagRepository2 studyStudyTagRepository2;

	private final StudyValidator studyValidator;

	// TODO: save할 때 카공장의 기존 스터디를 조회하는 로직에서 toStudy 메서드 사용하여 예외 발생
	@Transactional
	public Long saveWithCascade(Study study, Long memberId) {
		validateStudyDetails(study);

		Long savedStudyId = studyRepository2.save(study.getId(), study.getCafeId(), memberId);

		List<Long> studyTagIds = studyTagRepository2.countByTags(study.getTags());
		studyStudyTagRepository2.saveAll(savedStudyId, studyTagIds);

		// MemberEntity memberEntity = memberRepository.findById(memberId)
		// 	.orElseThrow(() -> new CafegoryException(MEMBER_NOT_FOUND));
		// CafeEntity cafeEntity = cafeRepository.findById(study.getCafeId())
		// 	.orElseThrow(() -> new CafegoryException(CAFE_NOT_FOUND));

		// CafeStudyEntity savedStudy2 =
		// 	cafeStudyRepository.save(buildCafeStudyEntity(study, cafeEntity, memberEntity));

		//		List<CafeStudyTagEntity> tags = cafeStudyTagRepository.findByTags(study.getTags());
		//		List<CafeStudyCafeStudyTagEntity> savedTags = cafeStudyCafeStudyTagRepository.saveAll(
		//			buildCafeStudyTags(savedStudy, tags));
		//		 savedStudy.addCafeStudyTags(savedTags);

		return savedStudyId;
	}

	private void validateStudyDetails(Study study) {
		studyValidator.validateEmptyOrWhiteSpace(study.getName(), STUDY_ONCE_NAME_EMPTY_OR_WHITESPACE);
		studyValidator.validateNameLength(study.getName());
		studyValidator.validateMaxParticipants(study.getMaxParticipantCount());
	}

	private CafeStudyEntity buildCafeStudyEntity(Study study, CafeEntity cafeEntity, MemberEntity memberEntity) {
		return CafeStudyEntity.builder()
			.name(study.getName())
			.cafe(cafeEntity)
			.coordinator(memberEntity)
			.studyPeriod(buildStudyPeriod(study))
			.memberComms(study.getMemberComms())
			.maxParticipants(study.getMaxParticipantCount())
			.build();
	}

	private StudyPeriod buildStudyPeriod(Study study) {
		return StudyPeriod.builder()
			.startDateTime(study.getStartDateTime())
			.endDateTime(study.getEndDateTime())
			.build();
	}

	private List<CafeStudyCafeStudyTagEntity> buildCafeStudyTags(
		CafeStudyEntity cafeStudy, List<CafeStudyTagEntity> cafeStudyTags
	) {
		return cafeStudyTags.stream()
			.map(cafeStudyTag -> CafeStudyCafeStudyTagEntity.builder()
				.cafeStudy(cafeStudy)
				.cafeStudyTag(cafeStudyTag)
				.build()
			)
			.collect(Collectors.toList());
	}

	@Transactional
	public void removeWithCascade(Long studyId, Long candidateCoordinatorId, LocalDateTime now) {
		Study study = studyQueryRepository2.findById(studyId);
		studyValidator.validateMemberIsCafeStudyCoordinator(candidateCoordinatorId, study.getCoordinator().getId());

		studyRepository2.deleteWithCascade(studyId, candidateCoordinatorId, now);
	}
}
