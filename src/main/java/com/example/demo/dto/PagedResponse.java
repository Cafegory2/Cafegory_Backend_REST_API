package com.example.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class PagedResponse<T> {
	private final int nowPage;
	private final int maxPage;
	private final int sizePerPage;
	private final List<T> results;
}
