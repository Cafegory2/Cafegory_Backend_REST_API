package com.example.demo.dto;

import lombok.Data;

@Data
public abstract class PagedSearchBase {
	protected int page;
	protected int sizePerPage;
}
