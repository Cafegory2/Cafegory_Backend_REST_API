package com.example.demo.service.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BusinessHourDto {

	private final String day;
	private final String startTime;
	private final String endTime;
	
}
