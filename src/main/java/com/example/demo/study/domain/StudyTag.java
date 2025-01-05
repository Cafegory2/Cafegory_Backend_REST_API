package com.example.demo.study.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudyTag {

	private final StudyTagId id;

	public boolean hasId(StudyTagId id) {
		return this.id.isSameId(id);
	}
}
