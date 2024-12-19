package com.example.demo.qna.service;


import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.config.ServiceTest;
import com.example.demo.exception.CafegoryException;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.qna.domain.CommentContent;
import com.example.demo.qna.infrastructure.CafeStudyCommentEntity;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.util.TimeUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.example.demo.exception.ExceptionType.CAFE_STUDY_COMMENT_HAS_REPLY;
import static com.example.demo.persister.CafeContextPersister.*;
import static com.example.demo.persister.CommentPersister.*;
import static com.example.demo.persister.MemberPersister.*;
import static com.example.demo.persister.StudyConextPersister.*;
import static org.assertj.core.api.Assertions.*;

class QnaServiceTest extends ServiceTest {

    @Autowired
    private QnaService sut;

    @Autowired
    private TimeUtil timeUtil;

    @Test
    @DisplayName("답변이 작성된 댓글은 수정할 수 없다.")
    void can_not_edit_comment_WithReplies() {
        //given
        CafeEntity cafe = aCafe().persist();

        MemberEntity coordinator = aMember().asCoordinator().persist();
        MemberEntity member = aMember().asParticipant().persist();

        CafeStudyEntity study = aStudy().withCafe(cafe).withMember(member).persist();
        CafeStudyCommentEntity rootComment = aComment().withStudy(study).withMember(member).persist();
        aComment().replyTo(rootComment).withStudy(study).withCoordinator(coordinator).persist();
        CommentContent commentContent = createCommentContent("변경된 댓글 내용", rootComment.getId());
        //when & then
        assertThatThrownBy(() -> sut.editComment(commentContent, member.getId()))
            .isInstanceOf(CafegoryException.class)
            .hasMessage(CAFE_STUDY_COMMENT_HAS_REPLY.getErrorMessage());
    }

    private CommentContent createCommentContent(String content, Long commentId) {
        return CommentContent.builder()
            .commentId(commentId)
            .content(content)
            .build();
    }

    @Test
    @DisplayName("답변이 작성된 댓글은 삭제할 수 없다.")
    void can_not_remove_comment_WithReplies() {
        //given
        CafeEntity cafe = aCafe().persist();

        MemberEntity coordinator = aMember().asCoordinator().persist();
        MemberEntity member = aMember().asParticipant().persist();

        CafeStudyEntity study = aStudy().withCafe(cafe).withMember(member).persist();
        CafeStudyCommentEntity rootComment = aComment().withStudy(study).withMember(member).persist();
        aComment().replyTo(rootComment).withStudy(study).withCoordinator(coordinator).persist();
        //when & then
        assertThatThrownBy(() -> sut.removeComment(rootComment.getId(), member.getId(), timeUtil.now()))
            .isInstanceOf(CafegoryException.class)
            .hasMessage(CAFE_STUDY_COMMENT_HAS_REPLY.getErrorMessage());
    }
}
