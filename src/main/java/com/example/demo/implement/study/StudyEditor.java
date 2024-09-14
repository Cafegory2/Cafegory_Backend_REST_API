package com.example.demo.implement.study;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.demo.implement.cafe.Cafe;
import com.example.demo.implement.member.Member;
import com.example.demo.mapper.CafeStudyMapper;
import com.example.demo.repository.study.CafeStudyRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StudyEditor {

	private final CafeStudyRepository cafeStudyRepository;
	private final CafeStudyMapper cafeStudyMapper;

	public Long createAndSaveCafeStudy(String studyName, Cafe cafe, Member coordinator, LocalDateTime startDateTime,
		LocalDateTime endDateTime, MemberComms memberComms, int maxParticipants) {
		CafeStudy cafeStudy = cafeStudyMapper.toNewEntity(studyName, cafe, coordinator, startDateTime, endDateTime,
			memberComms, maxParticipants);
		CafeStudy savedStudy = cafeStudyRepository.save(cafeStudy);

		return savedStudy.getId();
	}
}
