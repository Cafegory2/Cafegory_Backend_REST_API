package com.example.demo.qna.implement;

import com.example.demo.config.ServiceTest;
import com.example.demo.helper.CafeSaveHelper;
import com.example.demo.helper.CafeStudyCommentSaveHelper;
import com.example.demo.helper.CafeStudySaveHelper;
import com.example.demo.helper.MemberSaveHelper;
import com.example.demo.implement.cafe.CafeEntity;
import com.example.demo.implement.member.MemberEntity;
import com.example.demo.implement.study.CafeStudyEntity;
import com.example.demo.implement.study.StudyRole;
import com.example.demo.member.domain.MemberIdentity;
import com.example.demo.qna.domain.Comment;
import com.example.demo.qna.repository.CafeStudyCommentEntity;
import com.example.demo.repository.study.CafeStudyCommentRepository;
import com.example.demo.util.TimeUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class CommentEditorTest extends ServiceTest {

    @Autowired
    private CommentEditor sut;

    @Autowired
    private CafeStudySaveHelper cafeStudySaveHelper;
    @Autowired
    private MemberSaveHelper memberSaveHelper;
    @Autowired
    private CafeSaveHelper cafeSaveHelper;
    @Autowired
    private CafeStudyCommentSaveHelper cafeStudyCommentSaveHelper;

    @Autowired
    private CafeStudyCommentRepository cafeStudyCommentRepository;

    @Autowired
    private TimeUtil timeUtil;

    @Test
    @DisplayName("댓글을 저장한다.")
    void save_question() {
        //given
        CafeEntity cafe = cafeSaveHelper.saveCafe();

        MemberEntity member = memberSaveHelper.saveMember();

        LocalDateTime dateTime = timeUtil.localDateTime(2000, 1, 1, 12, 0, 0);
        CafeStudyEntity cafeStudy = cafeStudySaveHelper.saveCafeStudy(cafe, member, dateTime, dateTime.plusHours(2));

        Comment comment = createComment(cafeStudy, member, null);
        //when
        Long savedMemberId = sut.append(comment, member.getId());
        //then
        assertThat(savedMemberId).isNotNull();
    }

    @Test
    @DisplayName("대댓글을 저장한다.")
    void save_reply() {
        //given
        CafeEntity cafe = cafeSaveHelper.saveCafe();

        MemberEntity coordinator = memberSaveHelper.saveMember("coordinator@gmail.com");
        MemberEntity member = memberSaveHelper.saveMember("member@gmail.com");

        LocalDateTime dateTime = timeUtil.localDateTime(2000, 1, 1, 12, 0, 0);
        CafeStudyEntity cafeStudy = cafeStudySaveHelper.saveCafeStudy(cafe, member, dateTime, dateTime.plusHours(2));
        CafeStudyCommentEntity rootComment = cafeStudyCommentSaveHelper.saveRootComment(member, StudyRole.MEMBER, cafeStudy);

        Comment comment = createComment(cafeStudy, coordinator, rootComment);
        //when
        Long savedMemberId = sut.append(comment, member.getId());
        //then
        assertThat(savedMemberId).isNotNull();
    }

    private Comment createComment(
        CafeStudyEntity cafeStudy, MemberEntity member, CafeStudyCommentEntity parentCommentEntity) {
        return Comment.builder()
            .cafeStudyId(cafeStudy.getId())
            .member(
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
        CafeEntity cafe = cafeSaveHelper.saveCafeWith7daysFrom9To21();

        MemberEntity coordinator = memberSaveHelper.saveMember("coordinator@gmail.com");
        MemberEntity member = memberSaveHelper.saveMember("member@gmail.com");

        LocalDateTime startDateTime =
            timeUtil.localDateTime(2000, 1, 1, 10, 0, 0);
        CafeStudyEntity cafeStudy =
            cafeStudySaveHelper.saveCafeStudy(cafe, coordinator, startDateTime, startDateTime.plusHours(2));
        CafeStudyCommentEntity commentEntity =
            cafeStudyCommentSaveHelper.saveRootComment(member, StudyRole.MEMBER, cafeStudy);
        //when
        sut.edit("변경된 댓글 내용", commentEntity.getId());
        //then
        CafeStudyCommentEntity result = cafeStudyCommentRepository.findById(commentEntity.getId()).orElse(null);
        assertThat(result.getContent()).isEqualTo("변경된 댓글 내용");
    }
}