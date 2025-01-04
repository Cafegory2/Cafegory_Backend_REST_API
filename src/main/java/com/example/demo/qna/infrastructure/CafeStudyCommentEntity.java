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

import org.hibernate.annotations.Where;

import com.example.demo.auth.implement.BaseEntity;
import com.example.demo.domain.DateAudit;
import com.example.demo.member.domain.MemberId;
import com.example.demo.member.domain.MemberIdentity;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.qna.domain.Comment;
import com.example.demo.qna.domain.CommentContent;
import com.example.demo.qna.domain.CommentId;
import com.example.demo.qna.domain.ParentCommentId;
import com.example.demo.qna.domain.RootComment;
import com.example.demo.study.domain.StudyId;
import com.example.demo.study.domain.StudyRole;
import com.example.demo.study.infrastructure.CafeStudyEntity;

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

	public Comment toComment() {
		return Comment.builder()
			.id(new CommentId(this.id))
			.commentContent(
				CommentContent.builder()
					.content(this.content)
					.build()
			)
			.author(
				MemberIdentity.builder()
					.id(this.author.getId())
					.nickname(this.author.getNickname())
					.build()
			)
			.studyId(new StudyId(this.cafeStudy.getId()))
			.date(
				DateAudit.builder()
					.createdDate(getCreatedDate())
					.modifiedDate(getLastModifiedDate())
					.build()
			)
			.build();
	}

	public static CafeStudyCommentEntity of(RootComment comment, StudyRole studyRole) {
		return CafeStudyCommentEntity.builder()
			.author(new MemberEntity(comment.getAuthor().getId()))
			.content(comment.getContent())
			.parentComment(null)
			.studyRole(studyRole)
			.cafeStudy(new CafeStudyEntity(comment.getStudyId().getId()))
			.build();
	}

	public static CafeStudyCommentEntity createRootComment(
		CommentContent content, MemberId authorId, StudyId studyId, StudyRole studyRole
	) {
		return CafeStudyCommentEntity.builder()
			.author(new MemberEntity(authorId.getId()))
			.content(content.getContent())
			.studyRole(studyRole)
			.cafeStudy(new CafeStudyEntity(studyId.getId()))
			.build();
	}

	public static CafeStudyCommentEntity createSubComment(
		CommentContent content, ParentCommentId parentCommentId, MemberId authorId, StudyId studyId, StudyRole studyRole
	) {
		return CafeStudyCommentEntity.builder()
			.author(new MemberEntity(authorId.getId()))
			.content(content.getContent())
			.parentComment(new CafeStudyCommentEntity(parentCommentId.getId()))
			.studyRole(studyRole)
			.cafeStudy(new CafeStudyEntity(studyId.getId()))
			.build();
	}

	public void changeContent(String content) {
		this.content = content;
	}

	public boolean hasParentComment() {
		return this.parentComment != null;
	}
}
