package com.example.demo.service.study;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import com.example.demo.config.TestConfig;
import com.example.demo.domain.cafe.Cafe;
import com.example.demo.domain.member.Member;
import com.example.demo.domain.member.ThumbnailImage;
import com.example.demo.domain.study.StudyOnce;
import com.example.demo.dto.study.StudyOnceCommentRequest;
import com.example.demo.dto.study.StudyOnceCommentSaveRequest;
import com.example.demo.dto.study.StudyOnceCommentSearchListResponse;
import com.example.demo.dto.study.StudyOnceCommentSearchResponse;
import com.example.demo.dto.study.StudyOnceReplyResponse;
import com.example.demo.helper.save.CafeSaveHelper;
import com.example.demo.helper.save.MemberSaveHelper;
import com.example.demo.helper.save.StudyOnceSaveHelper;
import com.example.demo.helper.save.ThumbnailImageSaveHelper;

@SpringBootTest
@Import({TestConfig.class})
@Transactional
class StudyOnceCommentQueryServiceImplTest {

	@Autowired
	private EntityManager em;
	@Autowired
	private StudyOnceCommentService studyOnceCommentService;
	@Autowired
	private StudyOnceCommentQueryService studyOnceCommentQueryService;
	@Autowired
	private CafeSaveHelper cafePersistHelper;
	@Autowired
	private MemberSaveHelper memberPersistHelper;
	@Autowired
	private StudyOnceSaveHelper studyOncePersistHelper;
	@Autowired
	private ThumbnailImageSaveHelper thumbnailImagePersistHelper;

	@Test
	@DisplayName("댓글,대댓글 목록 조회 기능 ")
	void searchCommentsSortedByStudyOnceId() {
		ThumbnailImage thumbnailImage = thumbnailImagePersistHelper.persistDefaultThumbnailImage();
		Member leader = memberPersistHelper.persistDefaultMember(thumbnailImage);
		Cafe cafe = cafePersistHelper.persistDefaultCafe();
		StudyOnce studyOnce = studyOncePersistHelper.persistDefaultStudyOnce(cafe, leader);
		StudyOnceCommentSaveRequest questionRequest = new StudyOnceCommentSaveRequest("질문");
		Member member = memberPersistHelper.persistDefaultMember(thumbnailImage);
		Long questionId = studyOnceCommentService.saveQuestion(member.getId(), studyOnce.getId(), questionRequest);
		StudyOnceCommentRequest replyRequest = new StudyOnceCommentRequest("답변");
		studyOnceCommentService.saveReply(leader.getId(), studyOnce.getId(), questionId, replyRequest);
		em.flush();
		em.clear();

		StudyOnceCommentSearchListResponse commentsSearchResponse = studyOnceCommentQueryService.searchSortedCommentsByStudyOnceId(
			studyOnce.getId());
		List<StudyOnceCommentSearchResponse> comments = commentsSearchResponse.getComments();
		StudyOnceCommentSearchResponse actualQuestion = comments.get(0);
		StudyOnceReplyResponse actualReply = actualQuestion.getReplies().get(0);

		Assertions.assertAll(
			() -> assertThat(actualQuestion.getQuestionWriter().getMemberId()).isEqualTo(member.getId()),
			() -> assertThat(actualQuestion.getQuestionInfo().getComment()).isEqualTo(questionRequest.getContent()),
			() -> assertThat(actualReply.getComment()).isEqualTo(replyRequest.getContent())
		);
	}
}
