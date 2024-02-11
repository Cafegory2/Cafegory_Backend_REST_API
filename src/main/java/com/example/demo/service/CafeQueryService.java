package com.example.demo.service;

import com.example.demo.dto.CafeSearchRequest;
import com.example.demo.dto.CafeSearchResponse;
import com.example.demo.dto.PagedResponse;

public interface CafeQueryService {

	PagedResponse<CafeSearchResponse> searchWithPagingByDynamicFilter(CafeSearchRequest request);

}
