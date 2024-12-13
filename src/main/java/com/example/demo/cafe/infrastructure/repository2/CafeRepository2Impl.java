package com.example.demo.cafe.infrastructure.repository2;

import static com.example.demo.exception.ExceptionType.*;

import org.springframework.stereotype.Repository;

import com.example.demo.cafe.domain.Cafe;
import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.cafe.infrastructure.CafeRepository;
import com.example.demo.exception.CafegoryException;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CafeRepository2Impl implements CafeRepository2 {

	private final CafeRepository cafeJpaRepository;

	@Override
	public Cafe findById(Long cafeId) {
		CafeEntity cafeEntity = cafeJpaRepository.findById(cafeId)
			.orElseThrow(() -> new CafegoryException(CAFE_NOT_FOUND));

		return cafeEntity.toCafe();
	}

	@Override
	public Cafe findWithTags(Long cafeId) {
		CafeEntity cafeEntity = cafeJpaRepository.findWithTags(cafeId)
			.orElseThrow(() -> new CafegoryException(CAFE_NOT_FOUND));

		return cafeEntity.toCafeWithTagsAndMenu();
	}

	public CafeEntity findCafeEntityById(Long cafeId) {
		return cafeJpaRepository.findById(cafeId)
			.orElseThrow(() -> new CafegoryException(CAFE_NOT_FOUND));
	}
}
