package com.example.demo.implement.study;

import java.time.LocalDateTime;

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
import javax.persistence.UniqueConstraint;

import com.example.demo.implement.BaseEntity;
import com.example.demo.implement.member.MemberEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "deleted_date IS NULL")
@Table(name = "cafe_study_member", uniqueConstraints = {
	@UniqueConstraint(name = "unique_cafe_study_member", columnNames = {"cafe_study_id", "member_id"})})
public class CafeStudyMember extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "cafe_study_member_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cafe_study_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private CafeStudyEntity cafeStudy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private MemberEntity member;

	@Enumerated(EnumType.STRING)
	private StudyRole studyRole;

	@Enumerated(EnumType.STRING)
	private Attendance attendance;

	@Builder
	private CafeStudyMember(CafeStudyEntity cafeStudy, MemberEntity member, StudyRole studyRole) {
		this.cafeStudy = cafeStudy;
		this.member = member;
		this.studyRole = studyRole;
	}

	public boolean isConflictWith(LocalDateTime start, LocalDateTime end) {
		LocalDateTime studyStartDateTime = cafeStudy.getStudyPeriod().getStartDateTime();
		LocalDateTime studyEndDateTime = cafeStudy.getStudyPeriod().getEndDateTime();
		return (start.isBefore(studyEndDateTime) || start.isEqual(studyEndDateTime)) && (
			studyStartDateTime.isBefore(end) || studyStartDateTime.isEqual(end));
	}

	// public boolean isLeader(Member member) {
	// 	return this.id.getMemberId().equals(member.getId());
	// }

}
