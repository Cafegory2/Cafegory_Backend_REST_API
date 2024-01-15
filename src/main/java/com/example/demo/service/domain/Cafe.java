package com.example.demo.service.domain;

import com.example.demo.service.dto.PagedLogicResult;

public interface Cafe {
	default PagedLogicResult<Cafe> searchCafes() {
		throw new UnsupportedOperationException("미구현 메서드"); // ToDo 구현 필요
	}
}
