package com.example.demo.service.cafe;

import com.example.demo.dto.PagedResponse;
import com.example.demo.dto.cafe.CafeResponse;
import com.example.demo.dto.cafe.CafeSearchListResponse;
import com.example.demo.dto.cafe.CafeSearchRequest;

public interface CafeService {

	PagedResponse<CafeSearchListResponse> searchWithPagingByDynamicFilter(CafeSearchRequest request);

	CafeSearchListResponse searchCafeBasicInfoById(Long cafeId);

	CafeResponse searchCafeForMemberByCafeId(Long cafeId, Long memberId);

	CafeResponse searchCafeForNotMemberByCafeId(Long cafeId);

}
