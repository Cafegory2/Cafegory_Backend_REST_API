package com.example.demo.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.config.TestConfig;
import com.example.demo.domain.CafeImpl;
import com.example.demo.domain.MemberImpl;
import com.example.demo.domain.StudyOnceComment;
import com.example.demo.domain.StudyOnceImpl;
import com.example.demo.domain.ThumbnailImage;
import com.example.demo.dto.StudyOnceCommentRequest;
import com.example.demo.dto.StudyOnceCommentUpdateRequest;
import com.example.demo.exception.CafegoryException;
import com.example.demo.exception.ExceptionType;
import com.example.demo.helper.CafePersistHelper;
import com.example.demo.helper.MemberPersistHelper;
import com.example.demo.helper.StudyOnceCommentPersistHelper;
import com.example.demo.helper.StudyOncePersistHelper;
import com.example.demo.helper.ThumbnailImagePersistHelper;
import com.example.demo.repository.StudyOnceCommentRepository;

@SpringBootTest
@Transactional
@Import(TestConfig.class)
class StudyOnceCommentServiceImplTest {

	@Autowired
	private StudyOnceCommentService studyOnceCommentService;
	@Autowired
	private StudyOnceCommentRepository studyOnceCommentRepository;
	@Autowired
	private MemberPersistHelper memberPersistHelper;
	@Autowired
	private ThumbnailImagePersistHelper thumbnailImagePersistHelper;
	@Autowired
	private StudyOncePersistHelper studyOncePersistHelper;
	@Autowired
	private CafePersistHelper cafePersistHelper;
	@Autowired
	private StudyOnceCommentPersistHelper studyOnceCommentPersistHelper;
	@Autowired
	private EntityManager em;

	@Test
	@DisplayName("카공 질문을 저장한다.")
	void save_question() {
		//given
		ThumbnailImage thumb = thumbnailImagePersistHelper.persistDefaultThumbnailImage();
		MemberImpl leader = memberPersistHelper.persistMemberWithName(thumb, "카공장");
		MemberImpl otherPerson = memberPersistHelper.persistMemberWithName(thumb, "김동현");
		CafeImpl cafe = cafePersistHelper.persistDefaultCafe();
		StudyOnceImpl studyOnce = studyOncePersistHelper.persistDefaultStudyOnce(cafe, leader);
		//when
		studyOnceCommentService.saveQuestion(otherPerson.getId(), studyOnce.getId(),
			new StudyOnceCommentRequest("몇시까지 공부하시나요?"));
		em.flush();
		em.clear();
		List<StudyOnceComment> questions = studyOnceCommentRepository.findAll();
		//then
		assertThat(questions.size()).isEqualTo(1);
	}

	@Test
	@DisplayName("카공장의 카공 질문을 저장한다.")
	void save_question_by_leader() {
		//given
		ThumbnailImage thumb = thumbnailImagePersistHelper.persistDefaultThumbnailImage();
		MemberImpl leader = memberPersistHelper.persistMemberWithName(thumb, "카공장");
		CafeImpl cafe = cafePersistHelper.persistDefaultCafe();
		StudyOnceImpl studyOnce = studyOncePersistHelper.persistDefaultStudyOnce(cafe, leader);
		//when
		studyOnceCommentService.saveQuestion(leader.getId(), studyOnce.getId(),
			new StudyOnceCommentRequest("카페 끝날때까지 공부합니다."));
		em.flush();
		em.clear();
		List<StudyOnceComment> questions = studyOnceCommentRepository.findAll();
		//then
		assertThat(questions.size()).isEqualTo(1);
	}

	@Test
	@DisplayName("카공 질문을 수정한다.")
	void update_question() {
		//given
		ThumbnailImage thumb = thumbnailImagePersistHelper.persistDefaultThumbnailImage();
		MemberImpl leader = memberPersistHelper.persistMemberWithName(thumb, "카공장");
		MemberImpl otherPerson = memberPersistHelper.persistMemberWithName(thumb, "김동현");
		CafeImpl cafe = cafePersistHelper.persistDefaultCafe();
		StudyOnceImpl studyOnce = studyOncePersistHelper.persistDefaultStudyOnce(cafe, leader);
		StudyOnceComment question = studyOnceCommentPersistHelper.persistStudyOnceQuestionWithContent(
			otherPerson, studyOnce, "등록내용");
		//when
		studyOnceCommentService.updateComment(otherPerson.getId(), question.getId(),
			new StudyOnceCommentUpdateRequest("수정내용"));
		em.flush();
		em.clear();
		StudyOnceComment findQuestion = studyOnceCommentRepository.findById(question.getId()).get();
		//then
		assertThat(findQuestion.getContent()).isEqualTo("수정내용");
	}

	@Test
	@DisplayName("카공 질문은 질문한 회원 본인만 수정 할 수 있다.")
	void update_question_by_member_who_asked_the_question() {
		//given
		ThumbnailImage thumb = thumbnailImagePersistHelper.persistDefaultThumbnailImage();
		MemberImpl leader = memberPersistHelper.persistMemberWithName(thumb, "카공장");
		MemberImpl otherPerson = memberPersistHelper.persistMemberWithName(thumb, "김동현");
		CafeImpl cafe = cafePersistHelper.persistDefaultCafe();
		StudyOnceImpl studyOnce = studyOncePersistHelper.persistDefaultStudyOnce(cafe, leader);
		StudyOnceComment question = studyOnceCommentPersistHelper.persistStudyOnceQuestionWithContent(
			otherPerson, studyOnce, "등록내용");
		//when
		assertDoesNotThrow(() ->
			studyOnceCommentService.updateComment(otherPerson.getId(), question.getId(),
				new StudyOnceCommentUpdateRequest("수정내용"))
		);
	}

	@Test
	@DisplayName("카공 질문 수정은 질문한 회원 본인이 아니라면 예외가 터진다.")
	void update_question_by_member_who_not_asked_the_question_exception() {
		//given
		ThumbnailImage thumb = thumbnailImagePersistHelper.persistDefaultThumbnailImage();
		MemberImpl leader = memberPersistHelper.persistMemberWithName(thumb, "카공장");
		MemberImpl otherPerson = memberPersistHelper.persistMemberWithName(thumb, "김동현");
		CafeImpl cafe = cafePersistHelper.persistDefaultCafe();
		StudyOnceImpl studyOnce = studyOncePersistHelper.persistDefaultStudyOnce(cafe, leader);
		StudyOnceComment question = studyOnceCommentPersistHelper.persistStudyOnceQuestionWithContent(
			otherPerson, studyOnce, "등록내용");
		//when
		assertThatThrownBy(() ->
			studyOnceCommentService.updateComment(leader.getId(), question.getId(),
				new StudyOnceCommentUpdateRequest("수정내용"))
		).isInstanceOf(CafegoryException.class)
			.hasMessage(ExceptionType.STUDY_ONCE_COMMENT_PERMISSION_DENIED.getErrorMessage());
	}

	@Test
	@DisplayName("카공 질문을 삭제한다.")
	void delete_question() {
		//given
		ThumbnailImage thumb = thumbnailImagePersistHelper.persistDefaultThumbnailImage();
		MemberImpl leader = memberPersistHelper.persistMemberWithName(thumb, "카공장");
		MemberImpl otherPerson = memberPersistHelper.persistMemberWithName(thumb, "김동현");
		CafeImpl cafe = cafePersistHelper.persistDefaultCafe();
		StudyOnceImpl studyOnce = studyOncePersistHelper.persistDefaultStudyOnce(cafe, leader);
		StudyOnceComment question = studyOnceCommentPersistHelper.persistDefaultStudyOnceQuestion(
			otherPerson, studyOnce);
		//when
		studyOnceCommentService.deleteQuestion(question.getId());
		em.flush();
		em.clear();
		StudyOnceComment findQuestion = studyOnceCommentRepository.findById(question.getId()).orElse(null);
		//then
		assertThat(findQuestion).isNull();
	}

	@Test
	@DisplayName("카공 답변(대댓글)을 생성한다.")
	void saveReply() {
		//given
		ThumbnailImage thumb = thumbnailImagePersistHelper.persistDefaultThumbnailImage();
		MemberImpl leader = memberPersistHelper.persistMemberWithName(thumb, "카공장");
		MemberImpl otherPerson = memberPersistHelper.persistMemberWithName(thumb, "김동현");
		CafeImpl cafe = cafePersistHelper.persistDefaultCafe();
		StudyOnceImpl studyOnce = studyOncePersistHelper.persistDefaultStudyOnce(cafe, leader);
		StudyOnceComment question = studyOnceCommentPersistHelper.persistStudyOnceQuestionWithContent(
			otherPerson, studyOnce, "언제까지 공부하시나요?");
		studyOnceCommentPersistHelper.persistDefaultStudyOnceReply(leader, studyOnce, question);
		//when
		Long savedReplyId = studyOnceCommentService.saveReply(leader.getId(), studyOnce.getId(), question.getId(),
			new StudyOnceCommentRequest("카페 끝날때까지 공부합니다."));
		em.flush();
		em.clear();
		StudyOnceComment savedReply = studyOnceCommentRepository.findById(savedReplyId).get();
		//then
		assertThat(savedReply.getParent().getId()).isEqualTo(question.getId());

	}
}
