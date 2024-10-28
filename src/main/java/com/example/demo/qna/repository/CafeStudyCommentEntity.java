package com.example.demo.qna.repository;

import javax.persistence.*;

import com.example.demo.domain.DefaultDate;
import com.example.demo.implement.BaseEntity;
import com.example.demo.implement.member.MemberEntity;

import com.example.demo.implement.study.CafeStudyEntity;
import com.example.demo.implement.study.StudyRole;
import com.example.demo.member.domain.MemberIdentity;
import com.example.demo.qna.domain.Comment;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

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
    private CafeStudyCommentEntity(MemberEntity author, StudyRole studyRole, String content, CafeStudyCommentEntity parentComment, CafeStudyEntity cafeStudy) {
        this.author = author;
        this.studyRole = studyRole;
        this.content = content;
        this.parentComment = parentComment;
        this.cafeStudy = cafeStudy;
    }

    public Comment toComment() {
        return Comment.builder()
            .commentId(this.id)
            .member(
                MemberIdentity.builder()
                    .id(this.author.getId())
                    .nickname(this.author.getNickname())
                    .build()
            )
            .cafeStudyId(this.cafeStudy.getId())
            .content(this.content)
            .date(
                DefaultDate.builder()
                    .createdDate(getCreatedDate())
                    .lastModifiedDate(getLastModifiedDate())
                    .build()
            )
            .build();
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public boolean hasParentComment() {
        return this.parentComment != null;
    }

}
