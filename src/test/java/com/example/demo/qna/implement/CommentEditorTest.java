package com.example.demo.qna.implement;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.config.ServiceTest;
import com.example.demo.helper.CafeSaveHelper;
import com.example.demo.helper.CafeStudyCommentSaveHelper;
import com.example.demo.helper.CafeStudySaveHelper;
import com.example.demo.helper.MemberSaveHelper;
import com.example.demo.member.domain.MemberId;
import com.example.demo.member.domain.MemberIdentity;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.qna.domain.ChildComment;
import com.example.demo.qna.domain.CommentContent;
import com.example.demo.qna.domain.CommentId;
import com.example.demo.qna.domain.ParentCommentId;
import com.example.demo.qna.infrastructure.CafeStudyCommentEntity;
import com.example.demo.qna.infrastructure.CafeStudyCommentRepository;
import com.example.demo.study.domain.StudyId;
import com.example.demo.study.domain.StudyRole;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.util.TimeUtil;

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

		ChildComment comment = createComment("댓글 내용", cafeStudy.getId(), member, null);
		//when
		CommentId savedCommentId = sut.saveRootComment(
			CommentContent.builder().content("테스트 댓글 내용").build(), new StudyId(cafeStudy.getId()),
			new MemberId(member.getId()));
		//then
		assertThat(savedCommentId).isNotNull();
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
		CafeStudyCommentEntity rootComment = cafeStudyCommentSaveHelper.saveRootComment(member, StudyRole.MEMBER,
			cafeStudy);

		ChildComment comment = createComment("대댓글 내용", cafeStudy.getId(), coordinator, rootComment.getId());
		//when
		CommentId savedCommentId = sut.saveSubComment(
			CommentContent.builder().content("테스트 댓글 내용").build(), new ParentCommentId(rootComment.getId()),
			new StudyId(cafeStudy.getId()), new MemberId(member.getId()));
		//then
		assertThat(savedCommentId).isNotNull();
	}

	private ChildComment createComment(
		String content, Long cafeStudyId, MemberEntity member, Long parentCommentId) {
		return ChildComment.builder()
			.commentContent(
				CommentContent.builder()
					.content(content)
					.build()
			)
			.cafeStudyId(cafeStudyId)
			.parentCommentId(new ParentCommentId(parentCommentId))
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
		CafeEntity cafe = cafeSaveHelper.saveCafeWith7daysFrom9To21();

		MemberEntity coordinator = memberSaveHelper.saveMember("coordinator@gmail.com");
		MemberEntity member = memberSaveHelper.saveMember("member@gmail.com");

		LocalDateTime startDateTime =
			timeUtil.localDateTime(2000, 1, 1, 10, 0, 0);
		CafeStudyEntity cafeStudy =
			cafeStudySaveHelper.saveCafeStudy(cafe, coordinator, startDateTime, startDateTime.plusHours(2));
		CafeStudyCommentEntity commentEntity =
			cafeStudyCommentSaveHelper.saveRootComment(member, StudyRole.MEMBER, cafeStudy);
		ChildComment comment = createComment("변경된 댓글 내용", commentEntity.getId());
		//when
		sut.edit(comment);
		//then
		CafeStudyCommentEntity result = cafeStudyCommentRepository.findById(commentEntity.getId()).orElse(null);
		assertThat(result.getContent()).isEqualTo("변경된 댓글 내용");
	}

	private ChildComment createComment(String content, Long commentId) {
		return ChildComment.builder()
			.commentId(new CommentId(commentId))
			.commentContent(
				CommentContent.builder()
					.content(content)
					.build()
			)
			.build();
	}

	@Test
	@DisplayName("댓글을 삭제한다.")
	void remove_comment() {
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
		sut.remove(commentEntity.getId(), timeUtil.now());
		//then
		List<CafeStudyCommentEntity> result = cafeStudyCommentRepository.findAll();
		assertThat(result).hasSize(0);
	}
}