package com.example.demo.cafe.implement;

import com.example.demo.cafe.domain.Cafe;
import com.example.demo.cafe.infrastructure.CafeEntity;
import org.springframework.stereotype.Component;

import com.example.demo.exception.CafegoryException;
import com.example.demo.cafe.infrastructure.CafeRepository;

import lombok.RequiredArgsConstructor;

import static com.example.demo.exception.ExceptionType.*;

@Component
@RequiredArgsConstructor
public class CafeReader {

	private final CafeRepository cafeRepository;

	public Cafe read(Long cafeId) {
		CafeEntity cafeEntity = cafeRepository.findById(cafeId)
			.orElseThrow(() -> new CafegoryException(CAFE_NOT_FOUND));

		return cafeEntity.toCafe();
	}

	public CafeEntity getWithTagsEntity(Long cafeId) {
		 return cafeRepository.findWithTags(cafeId)
			 .orElseThrow(() -> new CafegoryException(CAFE_NOT_FOUND));
	}

	public Cafe getWithTags(Long cafeId) {
		CafeEntity cafeEntity = cafeRepository.findWithTags(cafeId)
			.orElseThrow(() -> new CafegoryException(CAFE_NOT_FOUND));

		return cafeEntity.toCafeWithTags();
	}
}
