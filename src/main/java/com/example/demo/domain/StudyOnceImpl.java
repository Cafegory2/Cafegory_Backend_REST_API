package com.example.demo.domain;

import java.time.Duration;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
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
	private boolean ableToTalk;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "leader_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private MemberImpl leader;

	private StudyOnceImpl(Long id, String name, CafeImpl cafe, LocalDateTime startDateTime, LocalDateTime endDateTime,
		int maxMemberCount, int nowMemberCount, boolean isEnd, boolean ableToTalk, MemberImpl leader) {
		validateStartDateTime(startDateTime);
		validateStudyOnceTime(startDateTime, endDateTime);
		this.id = id;
		this.name = name;
		this.cafe = cafe;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.maxMemberCount = maxMemberCount;
		this.nowMemberCount = nowMemberCount;
		this.isEnd = isEnd;
		this.ableToTalk = ableToTalk;
		this.leader = leader;
	}

	private static void validateStartDateTime(LocalDateTime startDateTime) {
		LocalDateTime now = LocalDateTime.now();
		Duration between = Duration.between(now, startDateTime);
		if (between.toSeconds() < 3 * 60 * 60) {
			throw new IllegalArgumentException("카공 시작 시간은 현재 시간보다 최소 3시간 이후여야 합니다.");
		}
	}

	private static void validateStudyOnceTime(LocalDateTime startDateTime, LocalDateTime endDateTime) {
		Duration between = Duration.between(startDateTime, endDateTime);
		if (between.toSeconds() < 60 * 60) {
			throw new IllegalArgumentException("카공 시간은 1시간 이상이어야 합니다.");
		}
		if (between.toSeconds() > 5 * 60 * 60) {
			throw new IllegalArgumentException("카공 시간은 5시간 미만이어야 합니다.");
		}
	}

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

	public boolean canJoin(LocalDateTime baseDateTime) {
		Duration between = Duration.between(baseDateTime, startDateTime);
		return between.toSeconds() >= 60 * 60;
	}
}
