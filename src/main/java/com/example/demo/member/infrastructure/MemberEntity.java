package com.example.demo.member.infrastructure;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.example.demo.auth.implement.BaseEntity;
import com.example.demo.domain.DateAudit;
import com.example.demo.member.domain.BeverageSize;
import com.example.demo.member.domain.Member;
import com.example.demo.member.domain.MemberContent;
import com.example.demo.member.domain.MemberId;
import com.example.demo.member.domain.Role;

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

	private String refreshToken;

	public MemberEntity(Long id) {
		this.id = id;
	}

	public MemberEntity(Member member) {
		this.role = member.getRole();
		this.nickname = member.getContent().getNickname();
		this.email = member.getEmail();
		this.profileUrl = member.getContent().getImgUrl();
		this.bio = member.getBio();
		this.beverageSize = member.getBeverageSize();
		this.refreshToken = member.getRefreshToken();
	}

	public Member toMember() {
		return Member.builder()
			.id(new MemberId(this.id))
			.content(
				MemberContent.builder()
					.nickname(this.nickname)
					.imgUrl(this.profileUrl)
					.build()
			)
			.role(this.role)
			.email(this.email)
			.bio(this.bio)
			.beverageSize(this.beverageSize)
			.dateAudit(
				DateAudit.builder()
					.createdDate(this.getCreatedDate())
					.modifiedDate(this.getLastModifiedDate())
					.build()
			)
			.refreshToken(this.refreshToken)
			.build();
	}
}
