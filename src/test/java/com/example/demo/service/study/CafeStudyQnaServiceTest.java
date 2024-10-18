package com.example.demo.service.study;

import com.example.demo.helper.CafeStudyCommentSaveHelper;
import com.example.demo.helper.CafeStudySaveHelper;
import com.example.demo.packageex.studyqna.service.CafeStudyQnaService;
import com.example.demo.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.config.ServiceTest;
import com.example.demo.helper.CafeSaveHelper;
import com.example.demo.helper.MemberSaveHelper;

class CafeStudyQnaServiceTest extends ServiceTest {

    @Autowired
    private CafeStudyQnaService sut;
    @Autowired
    private CafeSaveHelper cafeSaveHelper;
    @Autowired
    private MemberSaveHelper memberSaveHelper;
    @Autowired
    private CafeStudySaveHelper cafeStudySaveHelper;
    @Autowired
    private CafeStudyCommentSaveHelper cafeStudyCommentSaveHelper;
    @Autowired
    private TimeUtil timeUtil;

//    @Test
//    @DisplayName("회원은 Q&A에 댓글을 남길 수 있다.")
//    void write_comment() {
//        //given
//        Cafe cafe = cafeSaveHelper.saveCafeWith7daysFrom9To21();
//
//        Member coordinator = memberSaveHelper.saveMember();
//        Member member = memberSaveHelper.saveMember();
//
//        LocalDateTime startDateTime = timeUtil.localDateTime(2000, 1, 1, 10, 0, 0);
//        CafeStudy cafeStudy = cafeStudySaveHelper.saveCafeStudy(cafe, coordinator, startDateTime, startDateTime.plusHours(2));
//        //when
//        CafeStudyCommentCreateRequest request = CafeStudyCommentCreateRequest.builder()
//            .cafeStudyId(cafeStudy.getId())
//            .parentCommentId(null)
//            .content("Q&A 댓글 내용")
//            .build();
//        CafeStudyCommentCreateResponse response = sut.leaveComment(request);
//        //then
//        assertThat(response)
//            .extracting("content")
//            .isEqualTo("Q&A 댓글 내용");
//    }

//	@Test
//	@DisplayName("답변이 달리지 않은 질문은 수정 가능하다.")
//	void question_without_replies_can_be_updated() {
//		//given
//		ThumbnailImage thumbnailImage = thumbnailImageSaveHelper.saveThumbnailImage();
//		Member leader = memberSaveHelper.saveMember(thumbnailImage);
//		Member member = memberSaveHelper.saveMember(thumbnailImage);
//		Cafe cafe = cafePersistHelper.saveCafe();
//		StudyOnce studyOnce = studyOnceSaveHelper.saveStudyOnce(cafe, leader);
//		StudyOnceComment question = studyOnceCommentSaveHelper.saveStudyOnceQuestionWithContent(
//			member, studyOnce, "질문");
//		//when
//		sut.updateQuestion(member.getId(), question.getId(),
//			new StudyOnceCommentUpdateRequest("수정"));
//		StudyOnceComment findQuestion = studyOnceCommentRepository.findById(question.getId()).get();
//		//then
//		assertThat(findQuestion.getContent()).isEqualTo("수정");
//	}
//
//	@Test
//	@DisplayName("답변이 달린 질문은 수정할 수 없다.")
//	void question_with_replies_can_not_be_updated() {
//		//given
//		ThumbnailImage thumbnailImage = thumbnailImageSaveHelper.saveThumbnailImage();
//		Member leader = memberSaveHelper.saveMember(thumbnailImage);
//		Member member = memberSaveHelper.saveMember(thumbnailImage);
//		Cafe cafe = cafePersistHelper.saveCafe();
//		StudyOnce studyOnce = studyOnceSaveHelper.saveStudyOnce(cafe, leader);
//		StudyOnceComment question = studyOnceCommentSaveHelper.saveStudyOnceQuestionWithContent(
//			member, studyOnce, "질문");
//		studyOnceCommentSaveHelper.saveStudyOnceReplyWithContent(leader,
//			studyOnce, question, "답변");
//		//then
//		assertThatThrownBy(() -> sut.updateQuestion(member.getId(), question.getId(),
//			new StudyOnceCommentUpdateRequest("수정")))
//			.isInstanceOf(CafegoryException.class)
//			.hasMessage(ExceptionType.STUDY_ONCE_PARENT_COMMENT_MODIFICATION_BLOCKED.getErrorMessage());
//	}
//
//	@Test
//	@DisplayName("자신이 작성한 댓글만 수정 가능하다.")
//	void member_can_update_own_comment_only() {
//		//given
//		ThumbnailImage thumbnailImage = thumbnailImageSaveHelper.saveThumbnailImage();
//		Member leader = memberSaveHelper.saveMember(thumbnailImage);
//		Member member = memberSaveHelper.saveMember(thumbnailImage);
//		Cafe cafe = cafePersistHelper.saveCafe();
//		StudyOnce studyOnce = studyOnceSaveHelper.saveStudyOnce(cafe, leader);
//		StudyOnceComment comment = studyOnceCommentSaveHelper.saveStudyOnceQuestionWithContent(
//			member, studyOnce, "댓글");
//		//when
//		sut.updateQuestion(member.getId(), comment.getId(),
//			new StudyOnceCommentUpdateRequest("수정"));
//		//then
//		StudyOnceComment findComment = studyOnceCommentRepository.findById(comment.getId()).get();
//		assertThat(findComment.getContent()).isEqualTo("수정");
//	}
//
//	@Test
//	@DisplayName("다른 사람의 댓글을 수정할 수 없다.")
//	void member_can_not_update_another_members_comment() {
//		//given
//		ThumbnailImage thumbnailImage = thumbnailImageSaveHelper.saveThumbnailImage();
//		Member leader = memberSaveHelper.saveMember(thumbnailImage);
//		Member member = memberSaveHelper.saveMember(thumbnailImage);
//		Cafe cafe = cafePersistHelper.saveCafe();
//		StudyOnce studyOnce = studyOnceSaveHelper.saveStudyOnce(cafe, leader);
//		StudyOnceComment comment = studyOnceCommentSaveHelper.saveStudyOnceQuestionWithContent(
//			member, studyOnce, "댓글");
//		//then
//		assertThatThrownBy(() ->
//			sut.updateQuestion(leader.getId(), comment.getId(), new StudyOnceCommentUpdateRequest("수정"))
//		).isInstanceOf(CafegoryException.class)
//			.hasMessage(ExceptionType.STUDY_ONCE_COMMENT_PERMISSION_DENIED.getErrorMessage());
//	}
//
//	@Test
//	@DisplayName("답변이 달리지 않은 질문은 삭제 가능하다.")
//	void question_without_replies_can_be_deleted() {
//		//given
//		ThumbnailImage thumbnailImage = thumbnailImageSaveHelper.saveThumbnailImage();
//		Member leader = memberSaveHelper.saveMember(thumbnailImage);
//		Member member = memberSaveHelper.saveMember(thumbnailImage);
//		Cafe cafe = cafePersistHelper.saveCafe();
//		StudyOnce studyOnce = studyOnceSaveHelper.saveStudyOnce(cafe, leader);
//		StudyOnceComment question = studyOnceCommentSaveHelper.saveStudyOnceQuestionWithContent(
//			member, studyOnce, "질문");
//		//when
//		sut.deleteQuestion(question.getId());
//		//then
//		List<StudyOnceComment> comments = studyOnceCommentRepository.findAll();
//		assertThat(comments.isEmpty()).isTrue();
//	}
//
//	@Test
//	@DisplayName("답변이 달린 질문은 삭제할 수 없다.")
//	void question_with_replies_can_not_be_deleted() {
//		//given
//		ThumbnailImage thumbnailImage = thumbnailImageSaveHelper.saveThumbnailImage();
//		Member leader = memberSaveHelper.saveMember(thumbnailImage);
//		Member member = memberSaveHelper.saveMember(thumbnailImage);
//		Cafe cafe = cafePersistHelper.saveCafe();
//		StudyOnce studyOnce = studyOnceSaveHelper.saveStudyOnce(cafe, leader);
//		StudyOnceComment question = studyOnceCommentSaveHelper.saveStudyOnceQuestionWithContent(
//			member, studyOnce, "질문");
//		studyOnceCommentSaveHelper.saveStudyOnceReplyWithContent(leader,
//			studyOnce, question, "답변");
//		//then
//		assertThatThrownBy(() -> sut.deleteQuestion(question.getId()))
//			.isInstanceOf(CafegoryException.class)
//			.hasMessage(ExceptionType.STUDY_ONCE_PARENT_COMMENT_REMOVAL_BLOCKED.getErrorMessage());
//	}
//
//	@Test
//	@DisplayName("카공장만 답변할 수 있다.")
//	void only_leader_can_reply_to_questions() {
//		//given
//		ThumbnailImage thumbnailImage = thumbnailImageSaveHelper.saveThumbnailImage();
//		Member leader = memberSaveHelper.saveMember(thumbnailImage);
//		Member member1 = memberSaveHelper.saveMember(thumbnailImage);
//		Member member2 = memberSaveHelper.saveMember(thumbnailImage);
//		Cafe cafe = cafePersistHelper.saveCafe();
//		StudyOnce studyOnce = studyOnceSaveHelper.saveStudyOnce(cafe, leader);
//		StudyOnceComment question = studyOnceCommentSaveHelper.saveStudyOnceQuestionWithContent(
//			member1, studyOnce, "질문");
//		//then
//		assertThatThrownBy(
//			() -> sut.saveReply(member2.getId(), studyOnce.getId(), question.getId(),
//				new StudyOnceCommentRequest("답변")))
//			.isInstanceOf(CafegoryException.class)
//			.hasMessage(ExceptionType.STUDY_ONCE_REPLY_PERMISSION_DENIED.getErrorMessage());
//	}
//
//	@Test
//	@DisplayName("한개의 질문에 한개의 답변만 존재한다.")
//	void only_one_reply_allowed_per_question() {
//		//given
//		ThumbnailImage thumbnailImage = thumbnailImageSaveHelper.saveThumbnailImage();
//		Member leader = memberSaveHelper.saveMember(thumbnailImage);
//		Member member = memberSaveHelper.saveMember(thumbnailImage);
//		Cafe cafe = cafePersistHelper.saveCafe();
//		StudyOnce studyOnce = studyOnceSaveHelper.saveStudyOnce(cafe, leader);
//		StudyOnceComment question = studyOnceCommentSaveHelper.saveStudyOnceQuestionWithContent(
//			member, studyOnce, "질문1");
//		StudyOnceComment reply = studyOnceCommentSaveHelper.saveStudyOnceReplyWithContent(leader,
//			studyOnce, question, "답변");
//		//then
//		assertThatThrownBy(
//			() -> sut.saveReply(leader.getId(), studyOnce.getId(), reply.getId(),
//				new StudyOnceCommentRequest("질문2")))
//			.isInstanceOf(CafegoryException.class)
//			.hasMessage(ExceptionType.STUDY_ONCE_SINGLE_REPLY_PER_QUESTION.getErrorMessage());
//	}
}
