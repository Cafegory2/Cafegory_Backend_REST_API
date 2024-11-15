package com.example.demo.helper;

import org.springframework.transaction.annotation.Transactional;

import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.cafe.infrastructure.CafeKeywordEntity;
import com.example.demo.cafe.infrastructure.CafeKeywordRepository;
import com.example.demo.cafe.infrastructure.CafeRepository;
import com.example.demo.factory.TestCafeKeywordFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
public class CafeKeywordSaveHelper {

	private final CafeKeywordRepository cafeKeywordRepository;
	private final CafeRepository cafeRepository;

	public CafeKeywordEntity saveCafeKeyword(String keyword, CafeEntity cafe) {
		CafeEntity mergedCafe = cafeRepository.save(cafe);

		CafeKeywordEntity cafeKeyword = TestCafeKeywordFactory.createCafeKeyword(keyword, mergedCafe);
		return cafeKeywordRepository.save(cafeKeyword);
	}
}
