package com.example.demo.qna.service;


import com.example.demo.config.ServiceTest;
import com.example.demo.exception.CafegoryException;
import com.example.demo.helper.CafeSaveHelper;
import com.example.demo.helper.CafeStudyCommentSaveHelper;
import com.example.demo.helper.CafeStudySaveHelper;
import com.example.demo.helper.MemberSaveHelper;
import com.example.demo.implement.cafe.CafeEntity;
import com.example.demo.implement.member.MemberEntity;
import com.example.demo.implement.study.CafeStudyEntity;
import com.example.demo.implement.study.StudyRole;
import com.example.demo.qna.domain.Comment;
import com.example.demo.qna.repository.CafeStudyCommentEntity;
import com.example.demo.util.TimeUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static com.example.demo.exception.ExceptionType.CAFE_STUDY_COMMENT_HAS_REPLY;
import static org.assertj.core.api.Assertions.*;

class QnaServiceTest extends ServiceTest {

    @Autowired
    private QnaService sut;

    @Autowired
    private CafeStudySaveHelper cafeStudySaveHelper;
    @Autowired
    private MemberSaveHelper memberSaveHelper;
    @Autowired
    private CafeSaveHelper cafeSaveHelper;
    @Autowired
    private CafeStudyCommentSaveHelper cafeStudyCommentSaveHelper;

    @Autowired
    private TimeUtil timeUtil;

    @Test
    @DisplayName("답변이 작성된 댓글은 수정할 수 없다.")
    void can_not_edit_comment_WithReplies() {
        //given
        CafeEntity cafe = cafeSaveHelper.saveCafe();

        MemberEntity coordinator = memberSaveHelper.saveMember("coordinator@gmail.com");
        MemberEntity member = memberSaveHelper.saveMember("member@gmail.com");

        LocalDateTime dateTime = timeUtil.localDateTime(2000, 1, 1, 12, 0, 0);
        CafeStudyEntity cafeStudy = cafeStudySaveHelper.saveCafeStudy(cafe, member, dateTime, dateTime.plusHours(2));

        CafeStudyCommentEntity rootComment = cafeStudyCommentSaveHelper.saveRootComment(member, StudyRole.MEMBER, cafeStudy);
        cafeStudyCommentSaveHelper.saveReplyToParentComment(rootComment, coordinator, StudyRole.COORDINATOR, cafeStudy);
        Comment comment = createComment("변경된 댓글 내용", rootComment.getId());
        //when, then
        assertThatThrownBy(() -> sut.editComment(comment, member.getId()))
            .isInstanceOf(CafegoryException.class)
            .hasMessage(CAFE_STUDY_COMMENT_HAS_REPLY.getErrorMessage());
    }

    private Comment createComment(String content, Long commentId) {
        return Comment.builder()
            .commentId(commentId)
            .content(content)
            .build();
    }

}