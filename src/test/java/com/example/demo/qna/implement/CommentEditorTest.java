package com.example.demo.qna.implement;

import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.config.ServiceTest;
import com.example.demo.member.domain.MemberIdentity;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.qna.domain.Comment;
import com.example.demo.qna.domain.CommentContent;
import com.example.demo.qna.infrastructure.CafeStudyCommentEntity;
import com.example.demo.qna.infrastructure.CafeStudyCommentRepository;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.util.TimeUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.example.demo.persister.CafeContextPersister.*;
import static com.example.demo.persister.CommentPersister.*;
import static com.example.demo.persister.MemberPersister.*;
import static com.example.demo.persister.StudyConextPersister.*;
import static org.assertj.core.api.Assertions.*;

class CommentEditorTest extends ServiceTest {

    @Autowired
    private CommentEditor sut;

    @Autowired
    private CafeStudyCommentRepository cafeStudyCommentRepository;

    @Autowired
    private TimeUtil timeUtil;

    @Test
    @DisplayName("댓글을 저장한다.")
    void save_question() {
        //given
        CafeEntity cafe = aCafe().save();

        MemberEntity coordinator = aMember().asCoordinator().persist();
        CafeStudyEntity study = aStudy().withCafe(cafe).withMember(coordinator).persist();
        Comment comment = createComment("댓글 내용", study.getId(), coordinator, null);
        //when
        Long savedCommentId = sut.save(comment, coordinator.getId());
        //then
        assertThat(savedCommentId).isNotNull();
    }

    @Test
    @DisplayName("대댓글을 저장한다.")
    void save_reply() {
        //given
        CafeEntity cafe = aCafe().save();

        MemberEntity coordinator = aMember().asCoordinator().persist();
        MemberEntity member = aMember().asParticipant().persist();

        CafeStudyEntity study = aStudy().withCafe(cafe).withMember(coordinator).persist();
        CafeStudyCommentEntity rootComment = aComment().withStudy(study).withMember(member).persist();
        Comment comment = createComment("대댓글 내용", study.getId(), coordinator, rootComment.getId());
        //when
        Long savedCommentId = sut.save(comment, member.getId());
        //then
        assertThat(savedCommentId).isNotNull();
    }

    private Comment createComment(
        String content, Long cafeStudyId, MemberEntity member, Long parentCommentId) {
        return Comment.builder()
            .commentContent(
                CommentContent.builder()
                    .content(content)
                    .build()
            )
            .cafeStudyId(cafeStudyId)
            .parentCommentId(parentCommentId)
            .author(
                MemberIdentity.builder()
                    .id(member.getId())
                    .nickname(member.getNickname())
                    .build()
            )
            .build();
    }

    @Test
    @DisplayName("댓글을 수정한다.")
    void edit_comment() {
        //given
        CafeEntity cafe = aCafe().persistWith7daysFrom9To21();

        MemberEntity coordinator = aMember().asCoordinator().persist();
        MemberEntity member = aMember().asParticipant().persist();

        CafeStudyEntity study = aStudy().withCafe(cafe).withMember(coordinator).persist();
        CafeStudyCommentEntity commentEntity = aComment().withStudy(study).withMember(member).persist();
        Comment comment = createComment("변경된 댓글 내용", commentEntity.getId());
        //when
        sut.edit(comment);
        //then
        CafeStudyCommentEntity result = cafeStudyCommentRepository.findById(commentEntity.getId()).orElse(null);
        assertThat(result.getContent()).isEqualTo("변경된 댓글 내용");
    }

    private Comment createComment(String content, Long commentId) {
        return Comment.builder()
            .commentContent(
                CommentContent.builder()
                    .commentId(commentId)
                    .content(content)
                    .build()
            )
            .build();
    }

    @Test
    @DisplayName("댓글을 삭제한다.")
    void remove_comment() {
        //given
        CafeEntity cafe = aCafe().persistWith7daysFrom9To21();

        MemberEntity coordinator = aMember().asCoordinator().persist();
        MemberEntity member = aMember().asParticipant().persist();

        CafeStudyEntity study = aStudy().withCafe(cafe).withMember(coordinator).persist();
        CafeStudyCommentEntity commentEntity = aComment().withStudy(study).withMember(member).persist();
        //when
        sut.remove(commentEntity.getId(), timeUtil.now());
        //then
        List<CafeStudyCommentEntity> result = cafeStudyCommentRepository.findAll();
        assertThat(result).hasSize(0);
    }
}