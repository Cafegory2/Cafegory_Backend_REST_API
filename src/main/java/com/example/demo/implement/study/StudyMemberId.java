package com.example.demo.implement.study;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class StudyMemberId implements Serializable {
	@Column(name = "study_id")
	private Long studyId;

	@Column(name = "member_id")
	private Long memberId;
}
