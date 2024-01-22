package com.example.demo.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class StudyImpl implements Study {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn
	private CafeImpl cafe;

	private String name;

	private LocalDate date;

	private LocalTime startTime;

	private LocalTime endTime;

	private int maxMemberCount;

	private int nowMemberCount;

	private boolean isEnd;

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
