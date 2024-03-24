package com.example.demo.service;

import static org.assertj.core.api.Assertions.*;

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
import com.example.demo.domain.StudyOnceImpl;
import com.example.demo.domain.StudyOnceQuestion;
import com.example.demo.domain.ThumbnailImage;
import com.example.demo.helper.CafePersistHelper;
import com.example.demo.helper.MemberPersistHelper;
import com.example.demo.helper.StudyOncePersistHelper;
import com.example.demo.helper.ThumbnailImagePersistHelper;
import com.example.demo.repository.StudyOnceQuestionRepository;

@SpringBootTest
@Transactional
@Import(TestConfig.class)
class StudyOnceQuestionServiceImplTest {

	@Autowired
	private StudyOnceQuestionService studyOnceQuestionService;
	@Autowired
	private StudyOnceQuestionRepository studyOnceQuestionRepository;
	@Autowired
	private MemberPersistHelper memberPersistHelper;
	@Autowired
	private ThumbnailImagePersistHelper thumbnailImagePersistHelper;
	@Autowired
	private StudyOncePersistHelper studyOncePersistHelper;
	@Autowired
	private CafePersistHelper cafePersistHelper;
	@Autowired
	private EntityManager em;

	@Test
	@DisplayName("카공 질문을 저장한다.")
	void saveQuestion() {
		//given
		ThumbnailImage thumb = thumbnailImagePersistHelper.persistDefaultThumbnailImage();
		MemberImpl leader = memberPersistHelper.persistMemberWithName(thumb, "카공장");
		MemberImpl otherPerson = memberPersistHelper.persistMemberWithName(thumb, "김동현");
		CafeImpl cafe = cafePersistHelper.persistDefaultCafe();
		StudyOnceImpl studyOnce = studyOncePersistHelper.persistDefaultStudyOnce(cafe, leader);
		//when
		String content = "몇시까지 공부하시나요?";
		studyOnceQuestionService.saveQuestion(otherPerson.getId(), studyOnce.getId(), content);
		em.flush();
		em.clear();
		List<StudyOnceQuestion> questions = studyOnceQuestionRepository.findAll();
		//then
		assertThat(questions.size()).isEqualTo(1);
	}

	@Test
	@DisplayName("카공장의 카공 질문을 저장한다.")
	void saveQuestionByLeader() {
		//given
		ThumbnailImage thumb = thumbnailImagePersistHelper.persistDefaultThumbnailImage();
		MemberImpl leader = memberPersistHelper.persistMemberWithName(thumb, "카공장");
		CafeImpl cafe = cafePersistHelper.persistDefaultCafe();
		StudyOnceImpl studyOnce = studyOncePersistHelper.persistDefaultStudyOnce(cafe, leader);
		//when
		String content = "카페 끝날때까지 공부합니다.";
		studyOnceQuestionService.saveQuestion(leader.getId(), studyOnce.getId(), content);
		em.flush();
		em.clear();
		List<StudyOnceQuestion> questions = studyOnceQuestionRepository.findAll();
		//then
		assertThat(questions.size()).isEqualTo(1);
	}
}