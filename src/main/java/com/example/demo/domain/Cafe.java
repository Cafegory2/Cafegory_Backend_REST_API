package com.example.demo.domain;

import com.example.demo.service.dto.PagedLogicResult;

public interface Cafe {
	PagedLogicResult<Cafe> searchCafes();
}
