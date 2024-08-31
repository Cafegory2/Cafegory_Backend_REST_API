package com.example.demo.domain.study;

import java.time.LocalDateTime;

import javax.persistence.Embeddable;

import lombok.Getter;

@Embeddable
@Getter
public class StudyPeriod {

	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;

}
