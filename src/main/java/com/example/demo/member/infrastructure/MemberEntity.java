package com.example.demo.member.infrastructure;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.example.demo.domain.DateAudit;
import com.example.demo.member.domain.BeverageSize;
import com.example.demo.member.domain.Member;
import com.example.demo.member.domain.MemberIdentity;
import com.example.demo.member.domain.Role;
import com.example.demo.trash.implement.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "deleted_date IS NULL")
@Table(name = "member")
public class MemberEntity extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "member_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	private Role role;

	private String nickname;

	@Column(unique = true)
	private String email;

	private String profileUrl;
	private String bio;
	private int participationCount;

	@Enumerated(EnumType.STRING)
	private BeverageSize beverageSize;

	@Setter
	private String refreshToken;

	public void changeProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public MemberEntity(Long id) {
		this.id = id;
	}

	@Builder
	private MemberEntity(Role role, String nickname, String email, String profileUrl, String bio,
		int participationCount, BeverageSize beverageSize, String refreshToken) {
		this.role = role;
		this.nickname = nickname;
		this.email = email;
		this.profileUrl = profileUrl;
		this.bio = bio;
		this.participationCount = participationCount;
		this.beverageSize = beverageSize;
		this.refreshToken = refreshToken;
	}

	public Member toMember() {
		return Member.builder()
			.identity(
				MemberIdentity.builder()
					.id(this.id)
					.nickname(this.nickname)
					.build()
			)
			.role(this.role)
			.nickname(this.nickname)
			.email(this.email)
			.bio(this.bio)
			.beverageSize(this.beverageSize)
			.imgUrl(this.profileUrl)
			.dateAudit(
				DateAudit.builder()
					.createdDate(this.getCreatedDate())
					.modifiedDate(this.getLastModifiedDate())
					.build()
			)
			.refreshToken(this.refreshToken)
			.build();
	}

	public static MemberEntity fromMember(Member member) {
		return MemberEntity.builder()
			.role(member.getRole())
			.nickname(member.getNickname())
			.email(member.getEmail())
			.profileUrl(member.getImgUrl())
			.bio(member.getBio())
			.refreshToken(member.getRefreshToken())
			.build();
	}

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
