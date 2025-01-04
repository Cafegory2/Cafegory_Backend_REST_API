package com.example.demo.study.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Participant {

	private StudyMemberId id;
	private StudyId studyId;
	private StudyRole studyRole;
	private ParticipantContent content;
}
