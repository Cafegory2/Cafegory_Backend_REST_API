package com.example.demo.packageex.studyqna.repository;

import javax.persistence.*;

import com.example.demo.implement.BaseEntity;
import com.example.demo.implement.member.Member;

import com.example.demo.packageex.cafestudy.repository.CafeStudyEntity;
import com.example.demo.implement.study.StudyMemberRole;
import com.example.demo.packageex.member.domain.MemberIdentity;
import com.example.demo.packageex.studyqna.domain.CafeStudyComment;
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
    private Member author;

    private StudyMemberRole studyMemberRole;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CafeStudyCommentEntity parentComment;

    @OneToMany(mappedBy = "parentComment")
    private List<CafeStudyCommentEntity> childrenComments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_study_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CafeStudyEntity cafeStudyEntity;

    @Builder
    private CafeStudyCommentEntity(Member author, StudyMemberRole studyMemberRole, String content, CafeStudyCommentEntity parentComment, CafeStudyEntity cafeStudyEntity) {
        this.author = author;
        this.studyMemberRole = studyMemberRole;
        this.content = content;
        this.parentComment = parentComment;
        this.cafeStudyEntity = cafeStudyEntity;
    }

    public CafeStudyComment toComment() {
        return CafeStudyComment.builder()
            .cafeStudyCommentId(this.id)
            .member(
                MemberIdentity.builder()
                    .id(this.author.getId())
                    .nickname(this.author.getNickname())
                    .build()
            )
            .cafeStudyId(this.cafeStudyEntity.getId())
            .content(this.content)
            .build();
    }

    public boolean hasParentComment() {
        return this.parentComment != null;
    }
}
