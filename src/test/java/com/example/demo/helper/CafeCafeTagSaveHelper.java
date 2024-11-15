package com.example.demo.helper;

import org.springframework.transaction.annotation.Transactional;

import com.example.demo.cafe.infrastructure.CafeCafeTagEntity;
import com.example.demo.cafe.infrastructure.CafeCafeTagRepository;
import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.cafe.infrastructure.CafeRepository;
import com.example.demo.cafe.infrastructure.CafeTagEntity;
import com.example.demo.cafe.infrastructure.CafeTagRepository;
import com.example.demo.factory.TestCafeCafeTagFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
public class CafeCafeTagSaveHelper {

	private final CafeRepository cafeRepository;
	private final CafeTagRepository cafeTagRepository;
	private final CafeCafeTagRepository cafeCafeTagRepository;

	public CafeCafeTagEntity saveCafeCafeTag(CafeEntity cafe, CafeTagEntity cafeTag) {
		CafeEntity mergedCafe = cafeRepository.save(cafe);
		CafeTagEntity mergedCafeTag = cafeTagRepository.save(cafeTag);

		CafeCafeTagEntity cafeCafeTag = TestCafeCafeTagFactory.createCafeCafeTag(mergedCafe, mergedCafeTag);
		return cafeCafeTagRepository.save(cafeCafeTag);
	}
}
