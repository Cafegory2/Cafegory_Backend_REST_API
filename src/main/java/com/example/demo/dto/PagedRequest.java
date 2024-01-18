package com.example.demo.dto;

import lombok.Data;

@Data
public abstract class PagedRequest {
	protected int page;
	protected int sizePerPage;
}
