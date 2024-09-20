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
	@NotBlank // String 타입인 경우에는 @NotBlank 가능
	private String name;
//	@NotBlank Long타입에는 @NotBlank 불가능 @NotNull만 가능
	private Long cafeId;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private MemberComms memberComms;
//	@NotNull int타입은 @NotNull 불필요
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

