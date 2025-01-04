package com.example.demo.cafe.infrastructure.repository2;

import com.example.demo.auth.dto.SliceResponse;
import com.example.demo.cafe.domain.Cafe;
import com.example.demo.cafe.presentation.CafeSearchListRequest;

public interface CafeQueryRepository2 {

	SliceResponse<Cafe> findCafeByRegionAndKeyword(CafeSearchListRequest request);
}
