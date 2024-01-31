package com.example.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class PagedResponse<T> {
	private final int nowPage;
	private final int maxPage;
	private final int pageSize;
	private final List<T> results;

	public static <T> PagedResponse<T> createWithFirstPageAsOne(int nowPage, int maxPage, int pageSize,
		List<T> results) {
		return new PagedResponse<>(nowPage + 1, maxPage, pageSize, results);
	}
}
