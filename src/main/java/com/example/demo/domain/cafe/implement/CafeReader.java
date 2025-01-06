package com.example.demo.domain.cafe.implement;

import static com.example.demo.domain.exception.ExceptionType.*;

import org.springframework.stereotype.Component;

import com.example.demo.api.cafe.CafeSearchListRequest;
import com.example.demo.auth.dto.SliceResponse;
import com.example.demo.domain.cafe.domain.Cafe;
import com.example.demo.domain.cafe.domain.CafeId;
import com.example.demo.domain.cafe.repository.CafeQueryRepository;
import com.example.demo.domain.cafe.repository.CafeRepository;
import com.example.demo.domain.exception.CafegoryException;

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
