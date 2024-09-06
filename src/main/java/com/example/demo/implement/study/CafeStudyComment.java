package com.example.demo.implement.study;

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

import com.example.demo.implement.BaseEntity;
import com.example.demo.implement.member.Member;

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
@Table(name = "cafe_study_comment")
public class CafeStudyComment extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "cafe_study_comment_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Member author;

	private StudyRole content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_comment_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private CafeStudyComment parentComment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cafe_study_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private CafeStudy cafeStudy;

	// @Builder
	// private StudyOnceComment(Long id, String content, Member member, CafeStudy cafeStudy) {
	// 	//todo content에 대한 검증 추가
	// 	this.id = id;
	// 	this.content = content;
	// 	this.member = member;
	// 	this.cafeStudy = cafeStudy;
	// }
	//
	// public void addReply(StudyOnceComment reply) {
	// 	this.children.add(reply);
	// 	reply.parent = this;
	// }
	//
	// public void changeContent(String content) {
	// 	//todo content 에 대한 검증 추가
	// 	this.content = content;
	// }
	//
	// public boolean isPersonAsked(Member member) {
	// 	return this.member.getId().equals(member.getId());
	// }
	//
	// public boolean hasReply() {
	// 	return !this.children.isEmpty();
	// }
	//
	// public boolean hasParentComment() {
	// 	return this.parent != null;
	// }

}
