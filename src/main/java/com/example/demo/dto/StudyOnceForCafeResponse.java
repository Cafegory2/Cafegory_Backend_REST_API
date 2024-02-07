package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudyOnceForCafeResponse {
	private long cafeId;
	private long id;
	private String name;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private int maxMemberCount;
	private int nowMemberCount;
	private boolean isEnd;
}
