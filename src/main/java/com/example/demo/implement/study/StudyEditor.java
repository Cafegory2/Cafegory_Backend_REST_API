package com.example.demo.implement.study;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.exception.CafegoryException;
import com.example.demo.mapper.CafeStudyMapper;
import com.example.demo.member.domain.Member;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.repository.member.MemberRepository;
import com.example.demo.repository.study.CafeStudyRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StudyEditor {

	private final CafeStudyRepository cafeStudyRepository;
	private final CafeStudyMapper cafeStudyMapper;

	private final MemberRepository memberRepository;

	public Long createAndSaveCafeStudy(String studyName, CafeEntity cafe, Member coordinator,
		LocalDateTime startDateTime, LocalDateTime endDateTime, MemberComms memberComms, int maxParticipants) {

		MemberEntity coordinatorEntity = memberRepository.findById(coordinator.getIdentity().getId())
			.orElseThrow(() -> new CafegoryException(MEMBER_NOT_FOUND));

		CafeStudyEntity cafeStudy = cafeStudyMapper
			.toNewEntity(
				studyName, cafe, coordinatorEntity, startDateTime, endDateTime, memberComms, maxParticipants
			);
		CafeStudyEntity savedStudy = cafeStudyRepository.save(cafeStudy);

		return savedStudy.getId();
	}

	public Long deleteCafeStudy(CafeStudyEntity cafeStudy, LocalDateTime now) {
		cafeStudy.softDelete(now);

		return cafeStudy.getId();
	}
}
