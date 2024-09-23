package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.example.demo.helper.CafeSaveHelper;
import com.example.demo.helper.MemberSaveHelper;
import com.example.demo.repository.cafe.BusinessHourRepository;
import com.example.demo.repository.cafe.CafeRepository;
import com.example.demo.repository.member.MemberRepository;
import com.example.demo.util.TimeUtil;

@TestConfiguration
public class HelperConfig {

	@Autowired
	private CafeRepository cafeRepository;
	//	@Autowired
	//	private ReviewRepository reviewRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private BusinessHourRepository businessHourRepository;
	@Autowired
	private TimeUtil fakeTimeUtil;
	//	@Autowired
	//	private StudyMemberRepository studyMemberRepository;
	//	@Autowired
	//	private StudyOnceCommentRepository studyOnceCommentRepository;
	//	@Autowired
	//	private StudyOnceRepository studyOnceRepository;
	//	@Autowired
	//	private ThumbnailImageRepository thumbnailImageRepository;

	@Bean
	public CafeSaveHelper cafeSaveHelper() {
		return new CafeSaveHelper(cafeRepository, businessHourRepository, fakeTimeUtil);
	}

	@Bean
	public MemberSaveHelper memberSaveHelper() {
		return new MemberSaveHelper(memberRepository);
	}

	//	@Bean
	//	public ReviewSaveHelper reviewSaveHelper() {
	//		return new ReviewSaveHelper(reviewRepository, cafeRepository, memberRepository);
	//	}

	//	@Bean
	//	public StudyMemberSaveHelper studyMemberSaveHelper() {
	//		return new StudyMemberSaveHelper(studyMemberRepository, memberRepository, studyOnceRepository);
	//	}
	//
	//	@Bean
	//	public StudyOnceCommentSaveHelper studyOnceCommentSaveHelper() {
	//		return new StudyOnceCommentSaveHelper(studyOnceCommentRepository, memberRepository, studyOnceRepository);
	//	}
	//
	//	@Bean
	//	public StudyOnceSaveHelper studyOnceSaveHelper() {
	//		return new StudyOnceSaveHelper(studyOnceRepository, memberRepository, cafeRepository);
	//	}
	//
	//	@Bean
	//	public ThumbnailImageSaveHelper thumbnailImageSaveHelper() {
	//		return new ThumbnailImageSaveHelper(thumbnailImageRepository);
	//	}
}
