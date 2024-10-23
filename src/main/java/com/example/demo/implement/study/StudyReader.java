package com.example.demo.implement.study;

import static com.example.demo.exception.ExceptionType.*;

import org.springframework.stereotype.Component;

import com.example.demo.exception.CafegoryException;
import com.example.demo.repository.study.CafeStudyRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StudyReader {

	private final CafeStudyRepository cafeStudyRepository;

	public CafeStudyEntity getById(Long cafeStudyId) {
		return cafeStudyRepository.findById(cafeStudyId)
			.orElseThrow(() -> new CafegoryException(CAFE_STUDY_NOT_FOUND));
	}
}
