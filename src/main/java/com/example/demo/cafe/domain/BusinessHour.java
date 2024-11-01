package com.example.demo.cafe.domain;

import java.time.DayOfWeek;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BusinessHour {

	private Long id;
	private DayOfWeek dayOfWeek;
	private LocalTime openingTme;
	private LocalTime closingTme;
}
