package com.example.demo.domain.member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.demo.domain.BaseEntity;

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

	// TODO: 기획 확정 후 Entity 생성해야됨
	// private BeverageSize beverageSize;

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
