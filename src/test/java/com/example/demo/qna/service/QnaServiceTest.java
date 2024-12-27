package com.example.demo.qna.service;

import static com.example.demo.exception.ExceptionType.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.config.ServiceTest;
import com.example.demo.exception.CafegoryException;
import com.example.demo.helper.CafeSaveHelper;
import com.example.demo.helper.CafeStudyCommentSaveHelper;
import com.example.demo.helper.CafeStudySaveHelper;
import com.example.demo.helper.MemberSaveHelper;
import com.example.demo.member.domain.MemberId;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.qna.domain.CommentContent;
import com.example.demo.qna.domain.CommentId;
import com.example.demo.qna.infrastructure.CafeStudyCommentEntity;
import com.example.demo.study.domain.StudyRole;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.util.TimeUtil;

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

		CafeStudyCommentEntity rootComment = cafeStudyCommentSaveHelper.saveRootComment(member, StudyRole.MEMBER,
			cafeStudy);
		cafeStudyCommentSaveHelper.saveReplyToParentComment(rootComment, coordinator, StudyRole.COORDINATOR, cafeStudy);
		CommentContent commentContent = createCommentContent("변경된 댓글 내용");
		//when, then
		assertThatThrownBy(
			() -> sut.editComment(commentContent, new CommentId(rootComment.getId()), new MemberId(member.getId()))
		)
			.isInstanceOf(CafegoryException.class)
			.hasMessage(CAFE_STUDY_COMMENT_HAS_REPLY.getErrorMessage());
	}

	private CommentContent createCommentContent(String content) {
		return CommentContent.builder()
			.content(content)
			.build();
	}

	@Test
	@DisplayName("답변이 작성된 댓글은 삭제할 수 없다.")
	void can_not_remove_comment_WithReplies() {
		//given
		CafeEntity cafe = cafeSaveHelper.saveCafe();

		MemberEntity coordinator = memberSaveHelper.saveMember("coordinator@gmail.com");
		MemberEntity member = memberSaveHelper.saveMember("member@gmail.com");

		LocalDateTime dateTime = timeUtil.localDateTime(2000, 1, 1, 12, 0, 0);
		CafeStudyEntity cafeStudy = cafeStudySaveHelper.saveCafeStudy(cafe, member, dateTime, dateTime.plusHours(2));

		CafeStudyCommentEntity rootComment = cafeStudyCommentSaveHelper.saveRootComment(member, StudyRole.MEMBER,
			cafeStudy);
		cafeStudyCommentSaveHelper.saveReplyToParentComment(rootComment, coordinator, StudyRole.COORDINATOR, cafeStudy);
		//when, then
		assertThatThrownBy(
			() -> sut.removeComment(new CommentId(rootComment.getId()), new MemberId(member.getId()), timeUtil.now()))
			.isInstanceOf(CafegoryException.class)
			.hasMessage(CAFE_STUDY_COMMENT_HAS_REPLY.getErrorMessage());
	}
}