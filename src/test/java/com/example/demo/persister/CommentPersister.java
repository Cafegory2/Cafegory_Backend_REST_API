package com.example.demo.persister;

import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.qna.infrastructure.CafeStudyCommentEntity;
import com.example.demo.qna.infrastructure.CafeStudyCommentRepository;
import com.example.demo.study.domain.StudyRole;
import com.example.demo.study.infrastructure.CafeStudyEntity;

public class CommentPersister {

    private MemberEntity author;
    private StudyRole studyRole = StudyRole.MEMBER;
    private String content = "테스트 댓글 내용";
    private CafeStudyCommentEntity parentComment;
    private CafeStudyEntity cafeStudy;

    private CommentPersister() {}

    private CommentPersister(CommentPersister copy) {
        this.author = copy.author;
        this.studyRole = copy.studyRole;
        this.content = copy.content;
        this.parentComment = copy.parentComment;
        this.cafeStudy = copy.cafeStudy;
    }

    public CommentPersister but() {
        return new CommentPersister(this);
    }

    public static CommentPersister aComment() {
        return new CommentPersister();
    }

    public CommentPersister withAuthor(MemberEntity author) {
        this.author = author;
        return this;
    }

    public CommentPersister withCoordinator(MemberEntity coordinator) {
        this.author = coordinator;
        this.studyRole = StudyRole.COORDINATOR;
        return this;
    }

    public CommentPersister withMember(MemberEntity author) {
        this.author = author;
        this.studyRole = StudyRole.MEMBER;
        return this;
    }

    public CommentPersister withStudyRole(StudyRole studyRole) {
        this.studyRole = studyRole;
        return this;
    }

    public CommentPersister withContent(String content) {
        this.content = content;
        return this;
    }

    public CommentPersister replyTo(CafeStudyCommentEntity parentComment) {
        this.parentComment = parentComment;
        return this;
    }

    public CommentPersister withStudy(CafeStudyEntity cafeStudy) {
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

    public static class CommentRepoHolder {
        private static CafeStudyCommentRepository commentRepository;

        public static void init(CafeStudyCommentRepository commentRepo) {
            commentRepository = commentRepo;
        }
    }

    public CafeStudyCommentEntity save() {
        return CommentRepoHolder.commentRepository.save(build());
    }
}
