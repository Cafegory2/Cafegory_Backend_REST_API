package com.example.demo.implement.study;

import javax.persistence.*;

import com.example.demo.implement.BaseEntity;
import com.example.demo.implement.member.MemberEntity;

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
