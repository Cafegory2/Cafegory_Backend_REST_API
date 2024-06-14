package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.example.demo.helper.save.CafeSaveHelper;
import com.example.demo.helper.save.MemberSaveHelper;
import com.example.demo.helper.save.ReviewSaveHelper;
import com.example.demo.helper.save.StudyMemberSaveHelper;
import com.example.demo.helper.save.StudyOnceCommentSaveHelper;
import com.example.demo.helper.save.StudyOnceSaveHelper;
import com.example.demo.helper.save.ThumbnailImageSaveHelper;
import com.example.demo.repository.cafe.CafeRepository;
import com.example.demo.repository.member.MemberRepository;
import com.example.demo.repository.review.ReviewRepository;
import com.example.demo.repository.study.StudyMemberRepository;
import com.example.demo.repository.study.StudyOnceCommentRepository;
import com.example.demo.repository.study.StudyOnceRepository;
import com.example.demo.repository.thumbnailImage.ThumbnailImageRepository;

@TestConfiguration
public class TestConfig {

	@Autowired
	private CafeRepository cafeRepository;
	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private StudyMemberRepository studyMemberRepository;
	@Autowired
	private StudyOnceCommentRepository studyOnceCommentRepository;
	@Autowired
	private StudyOnceRepository studyOnceRepository;
	@Autowired
	private ThumbnailImageRepository thumbnailImageRepository;

	@Bean
	public CafeSaveHelper cafeSaveHelper() {
		return new CafeSaveHelper(cafeRepository);
	}

	@Bean
	public MemberSaveHelper memberSaveHelper() {
		return new MemberSaveHelper(memberRepository);
	}

	@Bean
	public ReviewSaveHelper reviewSaveHelper() {
		return new ReviewSaveHelper(reviewRepository);
	}

	@Bean
	public StudyMemberSaveHelper studyMemberSaveHelper() {
		return new StudyMemberSaveHelper(studyMemberRepository);
	}

	@Bean
	public StudyOnceCommentSaveHelper studyOnceCommentSaveHelper() {
		return new StudyOnceCommentSaveHelper(studyOnceCommentRepository);
	}

	@Bean
	public StudyOnceSaveHelper studyOnceSaveHelper() {
		return new StudyOnceSaveHelper(studyOnceRepository);
	}

	@Bean
	public ThumbnailImageSaveHelper thumbnailImageSaveHelper() {
		return new ThumbnailImageSaveHelper(thumbnailImageRepository);
	}

}
