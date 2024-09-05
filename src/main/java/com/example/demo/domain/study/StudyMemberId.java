package com.example.demo.domain.study;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class StudyMemberId implements Serializable {
	@Column(name = "study_id")
	private Long studyId;

	@Column(name = "member_id")
	private Long memberId;
}
