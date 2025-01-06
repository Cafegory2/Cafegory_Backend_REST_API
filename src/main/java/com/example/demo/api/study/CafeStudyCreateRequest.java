package com.example.demo.api.study;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.example.demo.domain.cafe.domain.CafeId;
import com.example.demo.domain.study.domain.CafeStudyTagType;
import com.example.demo.domain.study.domain.MemberComms;
import com.example.demo.domain.study.domain.Schedule;
import com.example.demo.domain.study.domain.StudyContent;

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
	private List<CafeStudyTagType> tags;

	@Builder
	private CafeStudyCreateRequest(String name, Long cafeId, LocalDateTime startDateTime, LocalDateTime endDateTime,
		MemberComms memberComms, int maxParticipants, String introduction, List<CafeStudyTagType> tags) {
		this.name = name;
		this.cafeId = cafeId;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.memberComms = memberComms;
		this.maxParticipants = maxParticipants;
		this.introduction = introduction;
		this.tags = tags;
	}

	public StudyContent toStudyContent() {
		return StudyContent.builder()
			.name(this.name)
			.schedule(
				Schedule.builder()
					.startDateTime(this.startDateTime)
					.endDateTime(this.endDateTime)
					.build()
			)
			.memberComms(this.memberComms)
			.maxParticipantCount(this.maxParticipants)
			.introduction(this.introduction)
			.tags(this.tags)
			.build();
	}

	public CafeId toCafeId() {
		return new CafeId(this.cafeId);
	}

	//	public Study toStudy() {
	//		return Study.builder()
	//			.name(this.name)
	//			.cafeId(new CafeId(this.cafeId))
	//			.schedule(
	//				Schedule.builder()
	//					.startDateTime(this.startDateTime)
	//					.endDateTime(this.endDateTime)
	//					.build()
	//			)
	//			.maxParticipantCount(this.maxParticipants)
	//			.memberComms(this.memberComms)
	//			.introduction(this.introduction)
	//			.tags(this.tags)
	//			.build();
	//	}
}

