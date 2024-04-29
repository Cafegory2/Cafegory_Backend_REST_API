package com.example.demo.service.cafe;

import com.example.demo.dto.PagedResponse;
import com.example.demo.dto.cafe.CafeSearchListResponse;
import com.example.demo.dto.cafe.CafeSearchRequest;
import com.example.demo.dto.cafe.CafeSearchResponse;

public interface CafeService {

	PagedResponse<CafeSearchListResponse> searchWithPagingByDynamicFilter(CafeSearchRequest request);

	CafeSearchListResponse searchCafeBasicInfoById(Long cafeId);

	CafeSearchResponse searchCafeForMemberByCafeId(Long cafeId, Long memberId);

	CafeSearchResponse searchCafeForNotMemberByCafeId(Long cafeId);

}
