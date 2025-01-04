package com.example.demo.cafe.infrastructure.repository2;

import org.springframework.stereotype.Repository;

import com.example.demo.auth.dto.SliceResponse;
import com.example.demo.cafe.domain.Cafe;
import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.cafe.infrastructure.CafeQueryDslRepository;
import com.example.demo.cafe.presentation.CafeSearchListRequest;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CafeQueryRepositoryImpl implements CafeQueryRepository {

	private final CafeQueryDslRepository cafeQueryRepository;

	@Override
	public SliceResponse<Cafe> findCafeByRegionAndKeyword(CafeSearchListRequest request) {
		SliceResponse<CafeEntity> response = cafeQueryRepository.findCafeByRegionAndKeyword(request);

		return response.map(CafeEntity::toCafe);
	}
}
