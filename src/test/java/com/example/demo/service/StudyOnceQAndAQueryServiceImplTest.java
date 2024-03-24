package com.example.demo.service;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.config.TestConfig;
import com.example.demo.domain.CafeImpl;
import com.example.demo.domain.MemberImpl;
import com.example.demo.domain.StudyOnceImpl;
import com.example.demo.domain.StudyOnceQuestion;
import com.example.demo.domain.ThumbnailImage;
import com.example.demo.dto.StudyOnceQuestionResponse;
import com.example.demo.helper.CafePersistHelper;
import com.example.demo.helper.MemberPersistHelper;
import com.example.demo.helper.StudyOncePersistHelper;
import com.example.demo.helper.StudyOnceQuestionPersistHelper;
import com.example.demo.helper.ThumbnailImagePersistHelper;

@SpringBootTest
@Import(TestConfig.class)
@Transactional
class StudyOnceQAndAQueryServiceImplTest {
	
	@Autowired
	private StudyOnceQAndAQueryService studyOnceQAndAQueryService;
	@Autowired
	private MemberPersistHelper memberPersistHelper;
	@Autowired
	private ThumbnailImagePersistHelper thumbnailImagePersistHelper;
	@Autowired
	private StudyOncePersistHelper studyOncePersistHelper;
	@Autowired
	private CafePersistHelper cafePersistHelper;
	@Autowired
	private StudyOnceQuestionPersistHelper studyOnceQuestionPersistHelper;
	@Autowired
	private EntityManager em;

	@Test
	@DisplayName("카공 질문을 찾는다")
	void searchQuestion() {
		//given
		ThumbnailImage thumb = thumbnailImagePersistHelper.persistDefaultThumbnailImage();
		MemberImpl leader = memberPersistHelper.persistMemberWithName(thumb, "카공장");
		MemberImpl otherPerson = memberPersistHelper.persistMemberWithName(thumb, "김동현");
		CafeImpl cafe = cafePersistHelper.persistDefaultCafe();
		StudyOnceImpl studyOnce = studyOncePersistHelper.persistDefaultStudyOnce(cafe, leader);
		StudyOnceQuestion studyOnceQuestion = studyOnceQuestionPersistHelper.persistDefaultStudyOnceQuestion(
			otherPerson, studyOnce);
		em.flush();
		em.clear();
		//when
		StudyOnceQuestionResponse response = studyOnceQAndAQueryService.searchQuestion(studyOnceQuestion.getId());
		Assertions.assertThat(response).isNotNull();
	}

}