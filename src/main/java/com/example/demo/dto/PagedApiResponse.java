package com.example.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class PagedApiResponse<T> {
	private int nowPage;
	private int maxPage;
	private int pageSize;
	private List<T> list;
}

