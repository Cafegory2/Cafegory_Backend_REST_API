package com.example.demo.implement.study;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.demo.implement.cafe.CafeEntity;
import com.example.demo.implement.member.MemberEntity;
import com.example.demo.mapper.CafeStudyMapper;
import com.example.demo.repository.study.CafeStudyRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StudyEditor {

	private final CafeStudyRepository cafeStudyRepository;
	private final CafeStudyMapper cafeStudyMapper;

	public Long createAndSaveCafeStudy(String studyName, CafeEntity cafeEntity, MemberEntity coordinator, LocalDateTime startDateTime,
                                       LocalDateTime endDateTime, MemberComms memberComms, int maxParticipants) {
		CafeStudy cafeStudy = cafeStudyMapper.toNewEntity(studyName, cafeEntity, coordinator, startDateTime, endDateTime,
			memberComms, maxParticipants);
		CafeStudy savedStudy = cafeStudyRepository.save(cafeStudy);

		return savedStudy.getId();
	}

	public Long deleteCafeStudy(CafeStudy cafeStudy, LocalDateTime now) {
		cafeStudy.softDelete(now);

		return cafeStudy.getId();
	}
}
