package com.example.demo.config;

import com.example.demo.helper.*;
import com.example.demo.repository.cafe.*;
import com.example.demo.repository.study.CafeStudyCafeStudyTagRepository;
import com.example.demo.repository.study.CafeStudyRepository;
import com.example.demo.repository.study.CafeStudyTagRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.example.demo.repository.member.MemberRepository;
import com.example.demo.util.TimeUtil;

@TestConfiguration
public class HelperConfig {

    @Bean
    public CafeSaveHelper cafeSaveHelper(
        CafeRepository cafeRepository, BusinessHourRepository businessHourRepository, TimeUtil timeUtil) {
        return new CafeSaveHelper(cafeRepository, businessHourRepository, timeUtil);
    }

    @Bean
    public CafeKeywordSaveHelper cafeKeywordSaveHelper(CafeKeywordRepository cafeKeywordRepository, CafeRepository cafeRepository) {
        return new CafeKeywordSaveHelper(cafeKeywordRepository, cafeRepository);
    }

    @Bean
    public MemberSaveHelper memberSaveHelper(MemberRepository memberRepository) {
        return new MemberSaveHelper(memberRepository);
    }

    @Bean
    public CafeStudySaveHelper cafeStudySaveHelper(
        CafeStudyRepository cafeStudyRepository, MemberRepository memberRepository, CafeRepository cafeRepository) {
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
}
