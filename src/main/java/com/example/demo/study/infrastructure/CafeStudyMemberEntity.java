package com.example.demo.study.infrastructure;

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

import lombok.*;
import org.hibernate.annotations.Where;

import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.study.domain.Attendance;
import com.example.demo.study.domain.Participant;
import com.example.demo.study.domain.ParticipantContent;
import com.example.demo.study.domain.StudyId;
import com.example.demo.study.domain.StudyMemberId;
import com.example.demo.study.domain.StudyRole;
import com.example.demo.auth.implement.BaseEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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

	//TODO 테스트 빌더 클래스 리팩터링을 위해 Setter로 임시로 오픈, 엔티티가 DB단에 완벽히 존재할 때 Setter없어도 수정 가능할듯
	@Setter
	@Enumerated(EnumType.STRING)
	private Attendance attendance;

	public CafeStudyMemberEntity(Long studyId, Long memberId) {
		this.cafeStudy = new CafeStudyEntity(studyId);
		this.member = new MemberEntity(memberId);
	}

	@Builder
	private CafeStudyMemberEntity(CafeStudyEntity cafeStudy, MemberEntity member, StudyRole studyRole) {
		this.cafeStudy = cafeStudy;
		this.member = member;
		this.studyRole = studyRole;
		this.attendance = Attendance.YES;
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
