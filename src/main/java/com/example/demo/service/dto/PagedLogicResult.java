package com.example.demo.service.dto;

import java.util.List;

import lombok.Data;

@Data
public class PagedLogicResult<T> {
	private final int nowPage;
	private final int maxPage;
	private final int sizePerPage;
	private final List<T> results;
}
