package com.example.demo.config;

import com.example.demo.helper.*;
import com.example.demo.repository.cafe.*;
import com.example.demo.repository.study.CafeStudyCafeStudyTagRepository;
import com.example.demo.repository.study.CafeStudyRepository;
import com.example.demo.repository.study.CafeStudyTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

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
    public CafeKeywordSaveHelper cafeKeywordSaveHelper(CafeKeywordRepository cafeKeywordRepository, CafeRepository cafeRepository) {
        return new CafeKeywordSaveHelper(cafeKeywordRepository, cafeRepository);
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
    @Bean
    public CafeStudySaveHelper cafeStudySaveHelper(CafeStudyRepository cafeStudyRepository) {
        return new CafeStudySaveHelper(cafeStudyRepository, memberRepository, cafeRepository);
    }

    @Bean
    public CafeStudyTagSaveHelper cafeStudyTagSaveHelper(CafeStudyTagRepository cafeStudyTagRepository) {
        return new CafeStudyTagSaveHelper(cafeStudyTagRepository);
    }

    @Bean
    public CafeStudyCafeStudyTagSaveHelper cafeStudyCafeStudyTagSaveHelper(
        CafeStudyRepository cafeStudyRepository, CafeStudyTagRepository cafeStudyTagRepository,
        CafeStudyCafeStudyTagRepository cafeStudyCafeStudyTagRepository
    ) {
        return new CafeStudyCafeStudyTagSaveHelper(cafeStudyRepository, cafeStudyTagRepository, cafeStudyCafeStudyTagRepository);
    }

    @Bean
    public CafeTagSaveHelper cafeTagSaveHelper(CafeTagRepository cafeTagRepository) {
        return new CafeTagSaveHelper(cafeTagRepository);
    }

    @Bean
    public CafeCafeTagSaveHelper cafeCafeTagSaveHelper(
        CafeRepository cafeRepository, CafeTagRepository cafeTagRepository,
        CafeCafeTagRepository cafeCafeTagRepository
    ) {
        return new CafeCafeTagSaveHelper(cafeRepository, cafeTagRepository, cafeCafeTagRepository);
    }

//
//	@Bean
//	public ThumbnailImageSaveHelper thumbnailImageSaveHelper() {
//		return new ThumbnailImageSaveHelper(thumbnailImageRepository);
//	}
}
