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
	private CafeSaveHelper cafePersistHelper;
	@Autowired
	private MemberSaveHelper memberPersistHelper;
	@Autowired
	private StudyOnceSaveHelper studyOncePersistHelper;
	@Autowired
	private StudyOnceCommentSaveHelper studyOnceCommentPersistHelper;
	@Autowired
	private ThumbnailImageSaveHelper thumbnailImagePersistHelper;
	@Autowired
	private StudyOnceQAndAQueryService studyOnceQAndAQueryService;

	@Test
	@DisplayName("카공 질문을 찾는다")
	void searchQuestion() {
		ThumbnailImage thumbnailImage = thumbnailImagePersistHelper.persistDefaultThumbnailImage();
		Member leader = memberPersistHelper.persistMemberWithName(thumbnailImage, "카공장");
		Member otherPerson = memberPersistHelper.persistMemberWithName(thumbnailImage, "김동현");
		Cafe cafe = cafePersistHelper.persistDefaultCafe();
		StudyOnce studyOnce = studyOncePersistHelper.persistDefaultStudyOnce(cafe, leader);
		StudyOnceComment studyOnceComment = studyOnceCommentPersistHelper.persistDefaultStudyOnceQuestion(
			otherPerson, studyOnce);

		StudyOnceCommentResponse response = studyOnceQAndAQueryService.searchComment(studyOnceComment.getId());

		Assertions.assertThat(response).isNotNull();
	}

}
