package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StudyMemberStateResponse {

	private final Long userId;
	private final boolean attendance;
	private final LocalDateTime lastUpdateTime;

}
