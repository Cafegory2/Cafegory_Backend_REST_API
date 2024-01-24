package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class StudyOnceCreateRequest {
	private long cafeId;
	private String name;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private int maxMemberCount;
	private boolean canTalk;
}

