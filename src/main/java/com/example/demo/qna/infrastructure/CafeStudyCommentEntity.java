package com.example.demo.qna.infrastructure;

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

import com.example.demo.study.domain.Study;
import org.hibernate.annotations.Where;

import com.example.demo.domain.DateAudit;
import com.example.demo.member.domain.MemberIdentity;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.qna.domain.Comment;
import com.example.demo.qna.domain.CommentContent;
import com.example.demo.study.domain.StudyRole;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.trash.implement.BaseEntity;

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

	public CafeStudyCommentEntity(Long id) {
		this.id = id;
	}

	@Builder
	private CafeStudyCommentEntity(MemberEntity author, StudyRole studyRole, String content,
		CafeStudyCommentEntity parentComment, CafeStudyEntity cafeStudy) {
		this.author = author;
		this.studyRole = studyRole;
		this.content = content;
		this.parentComment = parentComment;
		this.cafeStudy = cafeStudy;
	}

	public Comment toComment() {
		return Comment.builder()
			.commentContent(
				CommentContent.builder()
					.commentId(this.id)
					.content(this.content)
					.build()
			)
			.author(
				MemberIdentity.builder()
					.id(this.author.getId())
					.nickname(this.author.getNickname())
					.build()
			)
			.cafeStudyId(this.cafeStudy.getId())
			.date(
				DateAudit.builder()
					.createdDate(getCreatedDate())
					.modifiedDate(getLastModifiedDate())
					.build()
			)
			.build();
	}

	public static CafeStudyCommentEntity from(Comment comment, StudyRole studyRole) {
		return CafeStudyCommentEntity.builder()
				.author(new MemberEntity(comment.getAuthor().getId()))
				.content(comment.getContent())
				.parentComment(new CafeStudyCommentEntity(comment.getParentCommentId()))
				.studyRole(studyRole)
				.cafeStudy(new CafeStudyEntity(comment.getCafeStudyId()))
				.build();
	}

	public void changeContent(String content) {
		this.content = content;
	}

	public boolean hasParentComment() {
		return this.parentComment != null;
	}
}
