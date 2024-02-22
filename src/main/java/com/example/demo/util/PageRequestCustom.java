package com.example.demo.util;

import org.springframework.data.domain.Sort;

public class PageRequestCustom {

	private static final int DEFAULT_PAGE = 1;
	private static final int DEFAULT_SIZE = 10;
	private static final int MAX_SIZE = 30;
	private static final Sort.Direction DEFAULT_DIRECTION = Sort.Direction.DESC;

	public static org.springframework.data.domain.Pageable createByDefault() {
		return org.springframework.data.domain.PageRequest.of(DEFAULT_PAGE - 1, DEFAULT_SIZE, DEFAULT_DIRECTION,
			"id");
	}

	public static org.springframework.data.domain.Pageable of(int page, int size) {
		int validatedPage = (page <= 0) ? 1 : page;
		int validateSize = (size > MAX_SIZE ? DEFAULT_SIZE : size);
		return org.springframework.data.domain.PageRequest.of(validatedPage - 1, validateSize, DEFAULT_DIRECTION,
			"id");
	}

}
