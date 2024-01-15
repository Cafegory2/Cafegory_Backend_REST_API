package com.example.demo.controller.dto;

import java.util.List;

import lombok.Data;

@Data
public class PagedApiResponse<T> {
	private int nowPage;
	private int maxPage;
	private int pageSize;
	private List<T> list;
}

