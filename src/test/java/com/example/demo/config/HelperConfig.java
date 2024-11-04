package com.example.demo.config;

import com.example.demo.helper.*;
import com.example.demo.study.infrastructure.StudyMemberRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.example.demo.cafe.infrastructure.CafeRepository;
import com.example.demo.cafe.infrastructure.ReviewCafeTagRepository;
import com.example.demo.cafe.infrastructure.ReviewRepository;
import com.example.demo.repository.cafe.BusinessHourRepository;
import com.example.demo.repository.cafe.CafeCafeTagRepository;
import com.example.demo.repository.cafe.CafeKeywordRepository;
import com.example.demo.repository.cafe.CafeTagRepository;
import com.example.demo.repository.cafe.MenuRepository;
import com.example.demo.repository.member.MemberRepository;
import com.example.demo.repository.study.CafeStudyCafeStudyTagRepository;
import com.example.demo.qna.infrastructure.CafeStudyCommentRepository;
import com.example.demo.repository.study.CafeStudyTagRepository;
import com.example.demo.study.infrastructure.CafeStudyRepository;
import com.example.demo.util.TimeUtil;

@TestConfiguration
public class HelperConfig {

    @Bean
    public CafeSaveHelper cafeSaveHelper(
        CafeRepository cafeRepository, BusinessHourRepository businessHourRepository, TimeUtil timeUtil) {
        return new CafeSaveHelper(cafeRepository, businessHourRepository, timeUtil);
    }

    @Bean
    public CafeKeywordSaveHelper cafeKeywordSaveHelper(CafeKeywordRepository cafeKeywordRepository,
                                                       CafeRepository cafeRepository) {
        return new CafeKeywordSaveHelper(cafeKeywordRepository, cafeRepository);
    }

    @Bean
    public MemberSaveHelper memberSaveHelper(MemberRepository memberRepository) {
        return new MemberSaveHelper(memberRepository);
    }

    @Bean
    public CafeStudySaveHelper cafeStudySaveHelper(
        CafeStudyRepository cafeStudyRepository, MemberRepository memberRepository,
        CafeRepository cafeRepository, StudyMemberRepository studyMemberRepository
        ) {
        return new CafeStudySaveHelper(
            cafeStudyRepository, memberRepository, cafeRepository,
            cafeStudyMemberSaveHelper(
                studyMemberRepository, cafeStudyRepository, memberRepository));
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
        return new CafeStudyCafeStudyTagSaveHelper(cafeStudyRepository, cafeStudyTagRepository,
            cafeStudyCafeStudyTagRepository);
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

    @Bean
    public MenuSaveHelper menuSaveHelper(
        MenuRepository menuRepository, CafeRepository cafeRepository
    ) {
        return new MenuSaveHelper(menuRepository, cafeRepository);
    }

    @Bean
    public CafeStudyCommentSaveHelper cafeStudyCommentSaveHelper(
        CafeStudyCommentRepository cafeStudyCommentRepository,
        MemberRepository memberRepository, CafeStudyRepository cafeStudyRepository
    ) {
        return new CafeStudyCommentSaveHelper(cafeStudyCommentRepository, memberRepository, cafeStudyRepository);
    }

    @Bean
    public ReviewSaveHelper reviewSaveHelper(
        ReviewRepository reviewRepository, CafeRepository cafeRepository, MemberRepository memberRepository
    ) {
        return new ReviewSaveHelper(reviewRepository, cafeRepository, memberRepository);
    }

    @Bean
    public ReviewCafeTagSaveHelper reviewCafeTagSaveHelper(
        ReviewRepository reviewRepository, CafeTagRepository cafeTagRepository,
        ReviewCafeTagRepository reviewCafeTagRepository
    ) {
        return new ReviewCafeTagSaveHelper(reviewRepository, cafeTagRepository, reviewCafeTagRepository);
    }

    @Bean
    public CafeStudyMemberSaveHelper cafeStudyMemberSaveHelper(
        StudyMemberRepository studyMemberRepository, CafeStudyRepository cafeStudyRepository, MemberRepository memberRepository
    ) {
        return new CafeStudyMemberSaveHelper(studyMemberRepository, cafeStudyRepository, memberRepository);
    }
}
