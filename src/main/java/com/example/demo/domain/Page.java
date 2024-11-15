package com.example.demo.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Page {
	private int page;
	private int sizePerPage;
}
