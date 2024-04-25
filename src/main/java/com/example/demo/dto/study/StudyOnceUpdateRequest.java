package com.example.demo.dto.study;

import java.time.LocalDateTime;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudyOnceUpdateRequest {

	@Nullable
	private Long cafeId;
	@Nullable
	private String name;
	@Nullable
	private LocalDateTime startDateTime;
	@Nullable
	private LocalDateTime endDateTime;
	@Nullable
	private int maxMemberCount;
	@Nullable
	private boolean canTalk;
	@Nullable
	private String openChatUrl;
}
