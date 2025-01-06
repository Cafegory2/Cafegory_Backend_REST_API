package com.example.demo.db.cafe.repository2;

import com.example.demo.api.cafe.CafeSearchListRequest;
import com.example.demo.auth.dto.SliceResponse;
import com.example.demo.domain.cafe.domain.Cafe;

public interface CafeQueryRepository {

	SliceResponse<Cafe> findCafeByRegionAndKeyword(CafeSearchListRequest request);
}
