package com.example.demo.domain;

import java.time.LocalDateTime;

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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "study_once")
public class StudyOnceImpl implements StudyOnce {

	@Id
	@GeneratedValue
	@Column(name = "study_once_id")
	private Long id;

	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cafe_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private CafeImpl cafe;

	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private int maxMemberCount;
	private int nowMemberCount;
	private boolean isEnd;
	private boolean canTalk;

	@Override
	public void tryJoin(Member memberThatExpectedToJoin) {

	}

	@Override
	public void tryQuit(Member memberThatExpectedToQuit) {

	}

	@Override
	public void tryCancel(Member memberThatExpectedToCancel) {

	}

	@Override
	public void updateAttendance(Member leader, Member member, boolean attendance) {

	}
}
