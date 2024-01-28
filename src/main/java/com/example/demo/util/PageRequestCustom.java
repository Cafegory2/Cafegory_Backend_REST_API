package com.example.demo.util;

import org.springframework.data.domain.Sort;

public class PageRequestCustom {

	private static final int DEFAULT_PAGE = 1;
	private static final int DEFAULT_SIZE = 10;
	private static final int MAX_SIZE = 50;
	private static final Sort.Direction DEFAULT_DIRECTION = Sort.Direction.DESC;

	// private int page = 1;
	// private int size = 10;
	// private Sort.Direction direction = Sort.Direction.DESC;

	// private static void setPage(int page) {
	// 	this.page = page <= 0 ? 1 : page;
	// }

	// private static void setSize(int size) {
	// 	int DEFAULT_SIZE = 10;
	// 	int MAX_SIZE = 50;
	// 	this.size = size > MAX_SIZE ? DEFAULT_SIZE : size;
	// }

	// public void setDirection(Sort.Direction direction) {
	// 	this.direction = direction;
	// }

	public static org.springframework.data.domain.Pageable createByDefault() {
		return org.springframework.data.domain.PageRequest.of(DEFAULT_PAGE - 1, DEFAULT_SIZE, DEFAULT_DIRECTION,
			"create");
	}

	public static org.springframework.data.domain.Pageable of(int page, int size) {
		int validatedPage = (page <= 0) ? 1 : page;
		int validateSize = (size > MAX_SIZE ? DEFAULT_SIZE : size);
		return org.springframework.data.domain.PageRequest.of(validatedPage - 1, validateSize, DEFAULT_DIRECTION,
			"create");
	}

}
