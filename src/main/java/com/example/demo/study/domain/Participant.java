package com.example.demo.study.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Participant {

	private Long id;
	private Long studyId;
	private StudyRole studyRole;
	private ParticipantContent content;
}
