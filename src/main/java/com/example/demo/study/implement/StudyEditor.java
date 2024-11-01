package com.example.demo.study.implement;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.demo.cafe.domain.Cafe;
import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.cafe.infrastructure.CafeRepository;
import com.example.demo.exception.CafegoryException;
import com.example.demo.mapper.CafeStudyMapper;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.repository.member.MemberRepository;
import com.example.demo.study.domain.Study;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.study.infrastructure.CafeStudyRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StudyEditor {

	private final CafeStudyRepository cafeStudyRepository;
	private final CafeStudyMapper cafeStudyMapper;
	private final CafeRepository cafeRepository;

	private final StudyValidator studyValidator;

	private final MemberRepository memberRepository;

	public Long createAndSaveCafeStudy(Study study, Cafe cafe, Long memberId) {
		validateStudyDetails(study);

		MemberEntity coordinatorEntity = memberRepository.findById(memberId)
			.orElseThrow(() -> new CafegoryException(MEMBER_NOT_FOUND));

		CafeEntity cafeEntity = cafeRepository.findById(cafe.getId())
			.orElseThrow(() -> new CafegoryException(CAFE_NOT_FOUND));

		CafeStudyEntity cafeStudy = cafeStudyMapper
			.toNewEntity(
				study.getName(), cafeEntity, coordinatorEntity, study.getSchedule().getStartDateTime(),
				study.getSchedule().getEndDateTime(), study.getMemberComms(), study.getMaxParticipants()
			);
		CafeStudyEntity savedStudy = cafeStudyRepository.save(cafeStudy);

		return savedStudy.getId();
	}

	private void validateStudyDetails(Study study) {
		studyValidator.validateEmptyOrWhiteSpace(study.getName(), STUDY_ONCE_NAME_EMPTY_OR_WHITESPACE);
		studyValidator.validateNameLength(study.getName());
		studyValidator.validateMaxParticipants(study.getMaxParticipants());
	}

	public Long deleteCafeStudy(CafeStudyEntity cafeStudy, LocalDateTime now) {
		cafeStudy.softDelete(now);

		return cafeStudy.getId();
	}
}
