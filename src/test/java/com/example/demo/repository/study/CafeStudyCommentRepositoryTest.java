package com.example.demo.repository.study;

import com.example.demo.config.JpaTest;
import com.example.demo.helper.CafeSaveHelper;
import com.example.demo.helper.CafeStudyCommentSaveHelper;
import com.example.demo.helper.CafeStudySaveHelper;
import com.example.demo.helper.MemberSaveHelper;
import com.example.demo.implement.cafe.Cafe;
import com.example.demo.implement.member.Member;
import com.example.demo.implement.study.CafeStudy;
import com.example.demo.implement.study.CafeStudyComment;
import com.example.demo.implement.study.StudyRole;
import com.example.demo.util.TimeUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class CafeStudyCommentRepositoryTest extends JpaTest {

    @Autowired
    private CafeStudyCommentRepository sut;

    @Autowired
    private MemberSaveHelper memberSaveHelper;
    @Autowired
    private CafeSaveHelper cafeSaveHelper;
    @Autowired
    private CafeStudySaveHelper cafeStudySaveHelper;
    @Autowired
    private CafeStudyCommentSaveHelper cafeStudyCommentSaveHelper;
    @Autowired
    private TimeUtil timeUtil;


    @Test
    @DisplayName("카공 ID를 사용하여 해당 카공에 달린 모든 댓글과 대댓글을 조회한다.")
    void find_all_comments_with_replies_in_cafe_study() {
        //given
        Member coordinator = memberSaveHelper.saveMember("coordinator@gmail.com", "카공글 작성자");
        Member member1 = memberSaveHelper.saveMember("test1@gmail.com", "멤버1");
        Member member2 = memberSaveHelper.saveMember("test2@gmail.com", "멤버2");

        Cafe cafe = cafeSaveHelper.saveCafeWith7daysFrom9To21();

        LocalDateTime startDateTime = timeUtil.localDateTime(2000, 1, 1, 10, 0, 0);

        CafeStudy cafeStudy = cafeStudySaveHelper.saveCafeStudy(cafe, coordinator, startDateTime, startDateTime.plusHours(2));

        CafeStudyComment root1 = cafeStudyCommentSaveHelper.saveRootComment(member1, StudyRole.MEMBER, cafeStudy);
        CafeStudyComment replyToRoot1 = cafeStudyCommentSaveHelper.saveReplyToParentComment(root1, coordinator, StudyRole.COORDINATOR, cafeStudy);
        CafeStudyComment replyToReply1 = cafeStudyCommentSaveHelper.saveReplyToParentComment(replyToRoot1, member1, StudyRole.MEMBER, cafeStudy);

        CafeStudyComment root2 = cafeStudyCommentSaveHelper.saveRootComment(member2, StudyRole.MEMBER, cafeStudy);
        CafeStudyComment replyToRoot2 = cafeStudyCommentSaveHelper.saveReplyToParentComment(root2, coordinator, StudyRole.COORDINATOR, cafeStudy);
        CafeStudyComment replyToReply2 = cafeStudyCommentSaveHelper.saveReplyToParentComment(replyToRoot2, member1, StudyRole.MEMBER, cafeStudy);
        //when
        List<CafeStudyComment> result = sut.findAllBy(cafeStudy.getId());
        //then
        assertThat(result.size()).isEqualTo(6);
    }
}