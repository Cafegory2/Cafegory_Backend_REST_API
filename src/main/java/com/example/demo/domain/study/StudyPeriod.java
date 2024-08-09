package com.example.demo.domain.study;

import java.time.LocalTime;

import javax.persistence.Embeddable;

import lombok.Getter;

@Embeddable
@Getter
public class StudyPeriod {

	private LocalTime openingTime;
	private LocalTime closingTime;

}
