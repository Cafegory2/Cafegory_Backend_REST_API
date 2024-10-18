package com.example.demo.dto.study;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeStudyDeleteResponse {

	private Long cafeStudyId;

	private LocalDateTime deletedAt;

	@Builder
	private CafeStudyDeleteResponse(Long cafeStudyId, LocalDateTime deletedAt) {
		this.cafeStudyId = cafeStudyId;
		this.deletedAt = deletedAt;
	}
}
