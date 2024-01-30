package com.example.demo.controller.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CafeSearchRequest {
	private int page;
	private int sizePerPage = 10;
	private int startTime = 0;
	private int endTime = 24;
	private boolean canStudy;
	private int minBeveragePrice = 0;
	private int maxTime = 0;
	private String area;

}
