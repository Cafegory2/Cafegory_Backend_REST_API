package com.example.demo.cafe.implement;

import static com.example.demo.exception.ExceptionType.*;

import org.springframework.stereotype.Component;

import com.example.demo.cafe.domain.Cafe;
import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.cafe.infrastructure.CafeQueryRepository;
import com.example.demo.cafe.infrastructure.CafeRepository;
import com.example.demo.cafe.presentation.CafeSearchListRequest;
import com.example.demo.exception.CafegoryException;
import com.example.demo.trash.dto.SliceResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CafeReader {

	private final CafeRepository cafeRepository;
	private final CafeQueryRepository cafeQueryRepository;

	public Cafe read(Long cafeId) {
		CafeEntity cafeEntity = cafeRepository.findById(cafeId)
			.orElseThrow(() -> new CafegoryException(CAFE_NOT_FOUND));

		return cafeEntity.toCafe();
	}

	public Cafe getWithTags(Long cafeId) {
		CafeEntity cafeEntity = cafeRepository.findWithTags(cafeId)
			.orElseThrow(() -> new CafegoryException(CAFE_NOT_FOUND));

		return cafeEntity.toCafeWithTagsAndMenu();
	}

	public SliceResponse<CafeEntity> readCafes(CafeSearchListRequest request) {
		return cafeQueryRepository.findCafeByRegionAndKeyword(request);
	}
}
