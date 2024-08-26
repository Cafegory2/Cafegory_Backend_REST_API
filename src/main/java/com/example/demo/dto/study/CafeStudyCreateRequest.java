package com.example.demo.dto.study;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.example.demo.domain.study.MemberComms;
import com.sun.istack.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeStudyCreateRequest {
	@NotBlank
	private String name;
	private long cafeId;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private MemberComms memberComms;
	@NotNull
	private int maxParticipants;
	@NotBlank
	private String introduction;
}

