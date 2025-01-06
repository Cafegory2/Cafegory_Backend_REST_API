package com.example.demo.db.cafe.repository2;

import org.springframework.stereotype.Repository;

import com.example.demo.api.cafe.CafeSearchListRequest;
import com.example.demo.auth.dto.SliceResponse;
import com.example.demo.db.cafe.CafeEntity;
import com.example.demo.db.cafe.CafeQueryDslRepository;
import com.example.demo.domain.cafe.domain.Cafe;

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
