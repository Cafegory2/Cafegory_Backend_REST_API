package com.example.demo.service;

import com.example.demo.dto.PagedResponse;
import com.example.demo.service.dto.CafeSearchResponse;
import com.example.demo.service.dto.ServiceCafeSearchRequest;

public interface CafeQueryService {

	public PagedResponse<CafeSearchResponse> searchWithPagingByDynamicFilter(ServiceCafeSearchRequest request);

}
