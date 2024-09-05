package com.example.demo.implement.study;

import java.time.LocalDateTime;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyPeriod {

	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;

	@Builder
	private StudyPeriod(LocalDateTime startDateTime, LocalDateTime endDateTime) {
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}
}
