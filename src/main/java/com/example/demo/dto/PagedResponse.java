package com.example.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class PagedResponse<T> {
	private final int nowPage;
	private final int maxPage;
	private final int sizePerPage;
	private final List<T> results;

	public static <T> PagedResponse<T> createWithFirstPageAsOne(int nowPage, int maxPage, int sizePerPage,
		List<T> results) {
		return new PagedResponse<>(nowPage + 1, maxPage, sizePerPage, results);
	}
}
