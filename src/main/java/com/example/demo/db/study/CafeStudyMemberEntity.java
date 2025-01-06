package com.example.demo.db.study;

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

import org.hibernate.annotations.Where;

import com.example.demo.auth.implement.BaseEntity;
import com.example.demo.db.member.MemberEntity;
import com.example.demo.domain.study.domain.Attendance;
import com.example.demo.domain.study.domain.Participant;
import com.example.demo.domain.study.domain.ParticipantContent;
import com.example.demo.domain.study.domain.StudyId;
import com.example.demo.domain.study.domain.StudyMemberId;
import com.example.demo.domain.study.domain.StudyRole;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Where(clause = "deleted_date IS NULL")
@Table(name = "cafe_study_member", uniqueConstraints = {
	@UniqueConstraint(name = "unique_cafe_study_member", columnNames = {"cafe_study_id", "member_id"})})
public class CafeStudyMemberEntity extends BaseEntity {

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
	private Attendance attendance = Attendance.YES;

	public CafeStudyMemberEntity(Long studyId, Long memberId) {
		this.cafeStudy = new CafeStudyEntity(studyId);
		this.member = new MemberEntity(memberId);
	}

	public Participant toParticipant() {
		return Participant.builder()
			.id(new StudyMemberId(this.id))
			.studyId(new StudyId(this.cafeStudy.getId()))
			.studyRole(this.studyRole)
			.content(ParticipantContent.builder()
				.attendance(this.attendance)
				.build())
			.build();
	}
}
