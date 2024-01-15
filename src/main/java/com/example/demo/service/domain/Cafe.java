package com.example.demo.service.domain;

import com.example.demo.service.dto.PagedLogicResult;

public interface Cafe {
	PagedLogicResult<Cafe> searchCafes();
}
