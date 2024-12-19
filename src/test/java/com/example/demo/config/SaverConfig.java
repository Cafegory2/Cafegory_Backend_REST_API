package com.example.demo.config;

import com.example.demo.cafe.infrastructure.*;
import com.example.demo.member.infrastructure.MemberRepository;
import com.example.demo.qna.infrastructure.CafeStudyCommentRepository;
import com.example.demo.study.infrastructure.CafeStudyCafeStudyTagRepository;
import com.example.demo.study.infrastructure.CafeStudyRepository;
import com.example.demo.study.infrastructure.CafeStudyTagRepository;
import com.example.demo.study.infrastructure.StudyMemberRepository;
import com.example.demo.persister.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class SaverConfig {

    @Autowired
    private CafeRepository cafeRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CafeStudyRepository studyRepository;
    @Autowired
    private BusinessHourRepository businessHourRepository;
    @Autowired
    private CafeStudyCommentRepository commentRepository;
    @Autowired
    private CafeKeywordRepository cafeKeywordRepository;
    @Autowired
    private CafeStudyTagRepository studyTagRepository;
    @Autowired
    private CafeStudyCafeStudyTagRepository studyStudyTagRepository;
    @Autowired
    private CafeTagRepository cafeTagRepository;
    @Autowired
    private CafeCafeTagRepository cafeCafeTagRepository;
    @Autowired
    private StudyMemberRepository studyMemberRepository;
    @Autowired
    private MenuRepository menuRepository;

    public void init() {
        CafeContextPersister.CafeSaver.init(cafeRepository);
        BusinessHourPersister.BusinessHourSaver.init(businessHourRepository);
        CafeKeywordPersister.CafeKeywordSaver.init(cafeKeywordRepository);
        CafeTagPersister.CafeTagSaver.init(cafeTagRepository);
        CafeCafeTagPersister.CafeCafeTagSaver.init(cafeCafeTagRepository);
        MenuPersister.MenuSaver.init(menuRepository);

        MemberPersister.MemberSaver.init(memberRepository);

        StudyConextPersister.StudySaver.init(studyRepository);
        StudyTagPersister.StudyTagSaver.init(studyTagRepository);
        StudyStudyTagBuilder.StudyStudyTagSaver.init(studyStudyTagRepository);
        StudyMemberPersister.StudyMemberSaver.init(studyMemberRepository);

        CommentPersister.CommentSaver.init(commentRepository);
    }
}