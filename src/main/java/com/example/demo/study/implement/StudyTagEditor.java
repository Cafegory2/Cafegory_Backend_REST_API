package com.example.demo.study.implement;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.study.infrastructure.CafeStudyCafeStudyTagRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StudyTagEditor {

	private final CafeStudyCafeStudyTagRepository cafeStudyCafeStudyTagRepository;

	@Transactional
	public void removeStudyStudyTagBy(Long studyId, LocalDateTime now) {
		cafeStudyCafeStudyTagRepository.findByCafeStudy_Id(studyId)
			.forEach(studyTag -> studyTag.softDelete(now));
	}

}

