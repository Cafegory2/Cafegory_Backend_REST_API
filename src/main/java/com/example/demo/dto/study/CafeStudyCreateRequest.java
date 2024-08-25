package com.example.demo.dto.study;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.example.demo.domain.study.MemberComms;

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
	@NotBlank
	private long cafeId;
	@NotBlank
	private LocalDateTime startDateTime;
	@NotBlank
	private LocalDateTime endDateTime;
	@NotBlank
	private MemberComms memberComms;
	@NotBlank
	private int maxParticipants;
	@NotBlank
	private String introduction;
}

