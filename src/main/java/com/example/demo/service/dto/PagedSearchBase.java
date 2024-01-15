package com.example.demo.service.dto;

import lombok.Data;

@Data
public abstract class PagedSearchBase {
	protected int page;
	protected int sizePerPage;
}
