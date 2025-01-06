package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import com.example.demo.db.cafe.businessHour.BusinessHourJpaRepository;
import com.example.demo.db.cafe.cafe.CafeJpaRepository;
import com.example.demo.db.cafe.keyword.CafeKeywordJpaRepository;
import com.example.demo.db.cafe.menu.MenuJpaRepository;
import com.example.demo.db.cafe.review.ReviewCafeTagJpaRepository;
import com.example.demo.db.cafe.review.ReviewJpaRepository;
import com.example.demo.db.cafe.tag.CafeCafeTagJpaRepository;
import com.example.demo.db.cafe.tag.CafeTagJpaRepository;
import com.example.demo.db.member.MemberJpaRepository;
import com.example.demo.db.qna.CommentJpaRepository;
import com.example.demo.db.study.study.StudyJpaRepository;
import com.example.demo.db.study.studymember.StudyMemberJpaRepository;
import com.example.demo.db.study.tag.StudyStudyTagJpaRepository;
import com.example.demo.db.study.tag.StudyTagJpaRepository;
import com.example.demo.persister.BusinessHourPersister;
import com.example.demo.persister.CafeCafeTagPersister;
import com.example.demo.persister.CafeContextPersister;
import com.example.demo.persister.CafeKeywordPersister;
import com.example.demo.persister.CafeTagPersister;
import com.example.demo.persister.CommentPersister;
import com.example.demo.persister.MemberPersister;
import com.example.demo.persister.MenuPersister;
import com.example.demo.persister.ReviewCafeTagPersister;
import com.example.demo.persister.ReviewContextPersister;
import com.example.demo.persister.StudyConextPersister;
import com.example.demo.persister.StudyMemberPersister;
import com.example.demo.persister.StudyStudyTagBuilder;
import com.example.demo.persister.StudyTagPersister;

@TestComponent
public class RepositoryHolderConfig {

	@Autowired
	private CafeJpaRepository cafeJpaRepository;
	@Autowired
	private MemberJpaRepository memberJpaRepository;
	@Autowired
	private StudyJpaRepository studyRepository;
	@Autowired
	private BusinessHourJpaRepository businessHourRepository;
	@Autowired
	private CommentJpaRepository commentRepository;
	@Autowired
	private CafeKeywordJpaRepository cafeKeywordJpaRepository;
	@Autowired
	private StudyTagJpaRepository studyTagRepository;
	@Autowired
	private StudyStudyTagJpaRepository studyStudyTagRepository;
	@Autowired
	private CafeTagJpaRepository cafeTagJpaRepository;
	@Autowired
	private CafeCafeTagJpaRepository cafeCafeTagJpaRepository;
	@Autowired
	private StudyMemberJpaRepository studyMemberJpaRepository;
	@Autowired
	private MenuJpaRepository menuJpaRepository;
	@Autowired
	private ReviewJpaRepository reviewJpaRepository;
	@Autowired
	private ReviewCafeTagJpaRepository reviewCafeTagJpaRepository;

	public void init() {
		CafeContextPersister.CafeRepoHolder.init(cafeJpaRepository);
		BusinessHourPersister.BusinessHourRepoHolder.init(businessHourRepository);
		CafeKeywordPersister.CafeKeywordRepoHolder.init(cafeKeywordJpaRepository);
		CafeTagPersister.CafeTagRepoHolder.init(cafeTagJpaRepository);
		CafeCafeTagPersister.CafeCafeTagRepoHolder.init(cafeCafeTagJpaRepository);
		MenuPersister.MenuRepoHolder.init(menuJpaRepository);
		ReviewContextPersister.ReviewRepoHolder.init(reviewJpaRepository);
		ReviewCafeTagPersister.ReviewCafeTagRepoHolder.init(reviewCafeTagJpaRepository);

		MemberPersister.MemberRepoHolder.init(memberJpaRepository);

		StudyConextPersister.StudyRepoHolder.init(studyRepository);
		StudyTagPersister.StudyTagRepoHolder.init(studyTagRepository);
		StudyStudyTagBuilder.StudyStudyTagRepoHolder.init(studyStudyTagRepository);
		StudyMemberPersister.StudyMemberRepoHolder.init(studyMemberJpaRepository);

		CommentPersister.CommentRepoHolder.init(commentRepository);
	}
}