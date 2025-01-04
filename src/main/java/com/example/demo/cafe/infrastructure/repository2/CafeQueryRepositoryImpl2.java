package com.example.demo.cafe.infrastructure.repository2;

import org.springframework.stereotype.Repository;

import com.example.demo.auth.dto.SliceResponse;
import com.example.demo.cafe.domain.Cafe;
import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.cafe.infrastructure.CafeQueryRepository;
import com.example.demo.cafe.presentation.CafeSearchListRequest;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CafeQueryRepositoryImpl2 implements CafeQueryRepository2 {

	private final CafeQueryRepository cafeQueryRepository;

	@Override
	public SliceResponse<Cafe> findCafeByRegionAndKeyword(CafeSearchListRequest request) {
		SliceResponse<CafeEntity> response = cafeQueryRepository.findCafeByRegionAndKeyword(request);

		return response.map(CafeEntity::toCafe);
	}
}
