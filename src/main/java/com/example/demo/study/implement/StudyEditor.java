package com.example.demo.study.implement;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalDateTime;

import com.example.demo.cafe.domain.Cafe;
import com.example.demo.cafe.infrastructure.CafeRepository;
import org.springframework.stereotype.Component;

import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.exception.CafegoryException;
import com.example.demo.study.domain.MemberComms;
import com.example.demo.mapper.CafeStudyMapper;
import com.example.demo.member.domain.Member;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.repository.member.MemberRepository;
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

	public Long createAndSaveCafeStudy(String studyName, Cafe cafe, Member coordinator,
									   LocalDateTime startDateTime, LocalDateTime endDateTime, MemberComms memberComms, int maxParticipants) {
		studyValidator.validateEmptyOrWhiteSpace(studyName, STUDY_ONCE_NAME_EMPTY_OR_WHITESPACE);
		studyValidator.validateNameLength(studyName);

		MemberEntity coordinatorEntity = memberRepository.findById(coordinator.getIdentity().getId())
			.orElseThrow(() -> new CafegoryException(MEMBER_NOT_FOUND));

		CafeEntity cafeEntity = cafeRepository.findById(cafe.getId())
			.orElseThrow(() -> new CafegoryException(CAFE_NOT_FOUND));

		CafeStudyEntity cafeStudy = cafeStudyMapper
			.toNewEntity(
				studyName, cafeEntity, coordinatorEntity, startDateTime, endDateTime, memberComms, maxParticipants
			);
		CafeStudyEntity savedStudy = cafeStudyRepository.save(cafeStudy);

		return savedStudy.getId();
	}

	public Long deleteCafeStudy(CafeStudyEntity cafeStudy, LocalDateTime now) {
		cafeStudy.softDelete(now);

		return cafeStudy.getId();
	}
}
