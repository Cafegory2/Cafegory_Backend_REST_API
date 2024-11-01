package com.example.demo.implement.study;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.demo.study.domain.StudyRole;
import org.hibernate.annotations.Where;

import com.example.demo.implement.BaseEntity;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.study.infrastructure.CafeStudyEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "deleted_date IS NULL")
@Table(name = "cafe_study_comment")
public class CafeStudyCommentEntity extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "cafe_study_comment_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private MemberEntity author;

	private StudyRole studyRole;
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_comment_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private CafeStudyCommentEntity parentComment;

	@OneToMany(mappedBy = "parentComment")
	private List<CafeStudyCommentEntity> childrenComments = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cafe_study_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private CafeStudyEntity cafeStudy;

	@Builder
	private CafeStudyCommentEntity(MemberEntity author, StudyRole studyRole, String content,
		CafeStudyCommentEntity parentComment, CafeStudyEntity cafeStudy) {
		this.author = author;
		this.studyRole = studyRole;
		this.content = content;
		this.parentComment = parentComment;
		this.cafeStudy = cafeStudy;
	}

	//
	// public void addReply(StudyOnceComment reply) {
	// 	this.children.add(reply);
	// 	reply.parent = this;
	// }
	//
	// public void changeContent(String content) {
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
	public boolean hasParentComment() {
		return this.parentComment != null;
	}

}
