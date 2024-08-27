package com.example.demo.domain.member;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.demo.domain.BaseEntity;

import lombok.*;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "member")
public class Member extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "member_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	private Role role;

	private String nickname;
	private String email;
	private String profileUrl;
	private String bio;
	private int participationCount;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "beverage_size_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private BeverageSize beverageSize;

	@Setter
	private String refreshToken;

	// public void addStudyMember(StudyMember studyMember) {
	// 	this.studyMembers.add(studyMember);
	// }
	//
	// public void updateProfile(String name, String introduction) {
	// 	validateIntroduction(introduction);
	// 	this.name = name;
	// 	this.introduction = introduction;
	// }
	//
	// private void validateIntroduction(String introduction) {
	// 	if (introduction.length() > 300) {
	// 		throw new CafegoryException(PROFILE_UPDATE_INVALID_INTRODUCTION);
	// 	}
	// }
	//
	// public boolean hasStudyScheduleConflict(LocalDateTime start, LocalDateTime end) {
	// 	return this.studyMembers.stream()
	// 		.anyMatch(studyMember -> studyMember.isConflictWith(start, end));
	// }

}
