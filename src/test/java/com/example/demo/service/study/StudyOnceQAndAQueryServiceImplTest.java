package com.example.demo.service.study;

import javax.transaction.Transactional;

import org.assertj.core.api.Assertions;
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
import com.example.demo.domain.study.StudyOnceComment;
import com.example.demo.dto.study.StudyOnceCommentResponse;
import com.example.demo.helper.CafeSaveHelper;
import com.example.demo.helper.MemberSaveHelper;
import com.example.demo.helper.StudyOnceCommentSaveHelper;
import com.example.demo.helper.StudyOnceSaveHelper;
import com.example.demo.helper.ThumbnailImageSaveHelper;

@SpringBootTest
@Import({TestConfig.class})
@Transactional
class StudyOnceQAndAQueryServiceImplTest {

	@Autowired
	private CafeSaveHelper cafeSaveHelper;
	@Autowired
	private MemberSaveHelper memberSaveHelper;
	@Autowired
	private StudyOnceSaveHelper studyOnceSaveHelper;
	@Autowired
	private StudyOnceCommentSaveHelper studyOnceCommentSaveHelper;
	@Autowired
	private ThumbnailImageSaveHelper thumbnailImageSaveHelper;
	@Autowired
	private StudyOnceQAndAQueryService studyOnceQAndAQueryService;

	@Test
	@DisplayName("카공 질문을 찾는다")
	void searchQuestion() {
		ThumbnailImage thumbnailImage = thumbnailImageSaveHelper.saveDefaultThumbnailImage();
		Member leader = memberSaveHelper.saveMemberWithName(thumbnailImage, "카공장");
		Member otherPerson = memberSaveHelper.saveMemberWithName(thumbnailImage, "김동현");
		Cafe cafe = cafeSaveHelper.saveDefaultCafe();
		StudyOnce studyOnce = studyOnceSaveHelper.saveDefaultStudyOnce(cafe, leader);
		StudyOnceComment studyOnceComment = studyOnceCommentSaveHelper.saveDefaultStudyOnceQuestion(
			otherPerson, studyOnce);

		StudyOnceCommentResponse response = studyOnceQAndAQueryService.searchComment(studyOnceComment.getId());

		Assertions.assertThat(response).isNotNull();
	}

}
