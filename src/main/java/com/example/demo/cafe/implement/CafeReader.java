package com.example.demo.cafe.implement;

import static com.example.demo.exception.ExceptionType.*;

import org.springframework.stereotype.Component;

import com.example.demo.auth.dto.SliceResponse;
import com.example.demo.cafe.domain.Cafe;
import com.example.demo.cafe.domain.CafeId;
import com.example.demo.cafe.infrastructure.repository2.CafeQueryRepository;
import com.example.demo.cafe.infrastructure.repository2.CafeRepository;
import com.example.demo.cafe.presentation.CafeSearchListRequest;
import com.example.demo.exception.CafegoryException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CafeReader {

	private final CafeRepository cafeRepository;
	private final CafeQueryRepository cafeQueryRepository;

	public Cafe read(CafeId cafeId) {
		return cafeRepository.findById(cafeId)
			.orElseThrow(() -> new CafegoryException(CAFE_NOT_FOUND));
	}

	public Cafe getWithTags(CafeId cafeId) {
		return cafeRepository.findWithTags(cafeId)
			.orElseThrow(() -> new CafegoryException(CAFE_NOT_FOUND));
	}

	public SliceResponse<Cafe> readCafes(CafeSearchListRequest request) {
		return cafeQueryRepository.findCafeByRegionAndKeyword(request);
	}
}
