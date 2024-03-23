package com.example.demo.domain;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Getter
@Table(name = "study_once_question")
public class StudyOnceQuestion extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "study_once_question_id")
	private Long id;

	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private MemberImpl member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "study_once_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private StudyOnceImpl studyOnce;

}
