package com.example.demo.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BusinessHourResponse {

	private final String day;
	private final String startTime;
	private final String endTime;

}
