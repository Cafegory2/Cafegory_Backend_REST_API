package com.example.demo.dto.study;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.demo.implement.study.MemberComms;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeStudyCreateRequest {
	@NotBlank
	private String name;
	private Long cafeId;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private MemberComms memberComms;
	private int maxParticipants;
	@NotBlank
	private String introduction;

	@Builder
	private CafeStudyCreateRequest(String name, Long cafeId, LocalDateTime startDateTime, LocalDateTime endDateTime,
		MemberComms memberComms, int maxParticipants, String introduction) {
		this.name = name;
		this.cafeId = cafeId;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.memberComms = memberComms;
		this.maxParticipants = maxParticipants;
		this.introduction = introduction;
	}
}

