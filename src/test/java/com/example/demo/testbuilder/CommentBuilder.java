package com.example.demo.testbuilder;

import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.qna.infrastructure.CafeStudyCommentEntity;
import com.example.demo.qna.infrastructure.CafeStudyCommentRepository;
import com.example.demo.study.domain.StudyRole;
import com.example.demo.study.infrastructure.CafeStudyEntity;

import static com.example.demo.testbuilder.MemberBuilder.*;
import static com.example.demo.testbuilder.StudyBuilder.*;

public class CommentBuilder {

    private MemberEntity author = aMember().build();
    private StudyRole studyRole = StudyRole.MEMBER;
    private String content = "테스트 댓글 내용";
    private CafeStudyCommentEntity parentComment;
    private CafeStudyEntity cafeStudy = aStudy().build();

    private CommentBuilder() {}

    private CommentBuilder(CommentBuilder copy) {
        this.author = copy.author;
        this.studyRole = copy.studyRole;
        this.content = copy.content;
        this.parentComment = copy.parentComment;
        this.cafeStudy = copy.cafeStudy;
    }

    public CommentBuilder but() {
        return new CommentBuilder(this);
    }

    public static CommentBuilder aComment() {
        return new CommentBuilder();
    }

    public CommentBuilder with(MemberEntity author) {
        this.author = author;
        return this;
    }

    public CommentBuilder withCoordinator(MemberEntity coordinator) {
        this.author = coordinator;
        this.studyRole = StudyRole.COORDINATOR;
        return this;
    }

    public CommentBuilder withMember(MemberEntity author) {
        this.author = author;
        this.studyRole = StudyRole.MEMBER;
        return this;
    }

    public CommentBuilder withStudyRole(StudyRole studyRole) {
        this.studyRole = studyRole;
        return this;
    }

    public CommentBuilder withContent(String content) {
        this.content = content;
        return this;
    }

    public CommentBuilder replyTo(CafeStudyCommentEntity parentComment) {
        this.parentComment = parentComment;
        return this;
    }

    public CommentBuilder with(CafeStudyEntity cafeStudy) {
        this.cafeStudy = cafeStudy;
        return this;
    }

    public CafeStudyCommentEntity build() {
        return CafeStudyCommentEntity.builder()
            .author(this.author)
            .studyRole(this.studyRole)
            .content(this.content)
            .parentComment(this.parentComment)
            .cafeStudy(this.cafeStudy)
            .build();
    }

    public static class CommentSaver {
        private static CafeStudyCommentRepository commentRepository;

        public static void init(CafeStudyCommentRepository commentRepo) {
            commentRepository = commentRepo;
        }
    }

    public CafeStudyCommentEntity save() {
        return CommentSaver.commentRepository.save(build());
    }
}
