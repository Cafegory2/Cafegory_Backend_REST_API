package com.example.demo.domain.study;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "cafe_study_member")
public class CafeStudyMember {

	@Id
	@GeneratedValue
	@Column(name = "cafe_study_member_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cafe_study_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	// @MapsId("cafestudyId")
	private CafeStudy cafeStudy;

	// 복합키면 지워야함
	private Long id2;

	@Enumerated(EnumType.STRING)
	private StudyRole studyRole;

	@Enumerated(EnumType.STRING)
	private Attendance attendance;

	// @Builder
	// public StudyMember(Member member, CafeStudy study) {
	// 	this.member = member;
	// 	this.study = study;
	// 	id = new StudyMemberId(member.getId(), study.getId());
	// 	attendance = Attendance.YES;
	// }

	// public boolean isConflictWith(LocalDateTime start, LocalDateTime end) {
	// 	LocalDateTime studyStartDateTime = study.getStartDateTime();
	// 	LocalDateTime studyEndDateTime = study.getEndDateTime();
	// 	return (start.isBefore(studyEndDateTime) || start.isEqual(studyEndDateTime)) && (studyStartDateTime.isBefore(
	// 		end) || studyStartDateTime.isEqual(end));
	// }
	//
	// public boolean isLeader(Member member) {
	// 	return this.id.getMemberId().equals(member.getId());
	// }

}
