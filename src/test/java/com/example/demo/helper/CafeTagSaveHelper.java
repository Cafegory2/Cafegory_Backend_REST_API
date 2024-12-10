package com.example.demo.helper;

import org.springframework.transaction.annotation.Transactional;

import com.example.demo.cafe.domain.CafeTagType;
import com.example.demo.cafe.infrastructure.CafeTagEntity;
import com.example.demo.cafe.infrastructure.CafeTagRepository;
import com.example.demo.factory.TestCafeTagFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
public class CafeTagSaveHelper {

	private final CafeTagRepository cafeTagRepository;

	public CafeTagEntity saveCafeTag(CafeTagType cafeTagType) {
		CafeTagEntity cafeTag = TestCafeTagFactory.createCafeTag(cafeTagType);
		return cafeTagRepository.save(cafeTag);
	}
}
