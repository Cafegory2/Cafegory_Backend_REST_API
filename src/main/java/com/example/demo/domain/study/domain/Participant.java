package com.example.demo.domain.study.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Participant {

	private StudyMemberId id;
	private StudyId studyId;
	private StudyRole studyRole;
	private ParticipantContent content;

	public boolean hasId(StudyMemberId id) {
		return this.id.isSameId(id);
	}

	public boolean hasStudyId(StudyId id) {
		return this.studyId.isSameId(id);
	}
}
