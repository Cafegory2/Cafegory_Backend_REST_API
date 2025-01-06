package com.example.demo.service.qna.service;

import static com.example.demo.builder.CommentContentBuilder.*;
import static com.example.demo.exception.ExceptionType.*;
import static com.example.demo.persister.CafeContextPersister.*;
import static com.example.demo.persister.CommentPersister.*;
import static com.example.demo.persister.MemberPersister.*;
import static com.example.demo.persister.StudyConextPersister.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.config.ServiceTest;
import com.example.demo.db.cafe.CafeEntity;
import com.example.demo.db.member.MemberEntity;
import com.example.demo.db.qna.CafeStudyCommentEntity;
import com.example.demo.db.study.CafeStudyEntity;
import com.example.demo.domain.member.domain.MemberId;
import com.example.demo.domain.qna.domain.CommentContent;
import com.example.demo.domain.qna.domain.CommentId;
import com.example.demo.domain.qna.service.QnaService;
import com.example.demo.exception.CafegoryException;
import com.example.demo.util.TimeUtil;

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
		//when & then
		assertThatThrownBy(() -> sut.editComment(
			aCommentContent().withContent("변경된 댓글 내용").build(),
			new CommentId(rootComment.getId()), new MemberId(member.getId())))
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
		CafeEntity cafe = aCafe().persist();

		MemberEntity coordinator = aMember().asCoordinator().persist();
		MemberEntity member = aMember().asParticipant().persist();

		CafeStudyEntity study = aStudy().withCafe(cafe).withMember(member).persist();
		CafeStudyCommentEntity rootComment = aComment().withStudy(study).withMember(member).persist();
		aComment().replyTo(rootComment).withStudy(study).withCoordinator(coordinator).persist();
		//when & then
		assertThatThrownBy(() -> sut.removeComment(
			new CommentId(rootComment.getId()), new MemberId(member.getId()), timeUtil.now()))
			.isInstanceOf(CafegoryException.class)
			.hasMessage(CAFE_STUDY_COMMENT_HAS_REPLY.getErrorMessage());
	}
}
