package com.example.demo.helper;

import org.springframework.transaction.annotation.Transactional;

import com.example.demo.factory.TestCafeStudyTagFactory;
import com.example.demo.study.infrastructure.CafeStudyTagRepository;
import com.example.demo.study.domain.CafeStudyTagType;
import com.example.demo.study.infrastructure.CafeStudyTagEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
public class CafeStudyTagSaveHelper {

	private final CafeStudyTagRepository cafeStudyTagRepository;

	public CafeStudyTagEntity saveCafeStudyTag(CafeStudyTagType cafeStudyTagType) {
		CafeStudyTagEntity cafeStudyTag = TestCafeStudyTagFactory.createCafeStudyTag(cafeStudyTagType);
		return cafeStudyTagRepository.save(cafeStudyTag);
	}

}
