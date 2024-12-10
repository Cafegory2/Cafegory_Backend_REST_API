package com.example.demo.helper;

import org.springframework.transaction.annotation.Transactional;

import com.example.demo.factory.TestCafeStudyCafeStudyTagFactory;
import com.example.demo.study.infrastructure.CafeStudyCafeStudyTagRepository;
import com.example.demo.study.infrastructure.CafeStudyTagRepository;
import com.example.demo.study.infrastructure.CafeStudyCafeStudyTagEntity;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.study.infrastructure.CafeStudyRepository;
import com.example.demo.study.infrastructure.CafeStudyTagEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
public class CafeStudyCafeStudyTagSaveHelper {

	private final CafeStudyRepository cafeStudyRepository;
	private final CafeStudyTagRepository cafeStudyTagRepository;
	private final CafeStudyCafeStudyTagRepository studyCafeStudyTagRepository;

	public CafeStudyCafeStudyTagEntity saveCafeStudyCafeStudyTag(CafeStudyEntity cafeStudy,
		CafeStudyTagEntity cafeStudyTag) {
		CafeStudyEntity mergedCafeStudy = cafeStudyRepository.save(cafeStudy);
		CafeStudyTagEntity mergedCafeStudyTag = cafeStudyTagRepository.save(cafeStudyTag);

		CafeStudyCafeStudyTagEntity cafeStudyCafeStudyTag = TestCafeStudyCafeStudyTagFactory.createCafeStudyCafeStudyTag(
			mergedCafeStudy, mergedCafeStudyTag);
		return studyCafeStudyTagRepository.save(cafeStudyCafeStudyTag);
	}
}
