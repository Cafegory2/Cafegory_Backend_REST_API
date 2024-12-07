package com.example.demo.config;

import com.example.demo.cafe.infrastructure.CafeRepository;
import com.example.demo.qna.infrastructure.CafeStudyCommentRepository;
import com.example.demo.repository.cafe.BusinessHourRepository;
import com.example.demo.repository.cafe.CafeKeywordRepository;
import com.example.demo.repository.member.MemberRepository;
import com.example.demo.study.infrastructure.CafeStudyRepository;
import com.example.demo.testbuilder.*;
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

    public void init() {
        CafeBuilder.CafeSaver.init(cafeRepository);
        CafeBuilder.BusinessHourSaver.init(businessHourRepository);

        MemberBuilder.MemberSaver.init(memberRepository);

        StudyBuilder.StudySaver.init(studyRepository);

        CafeKeywordBuilder.CafeKeywordSaver.init(cafeKeywordRepository);

        CommentBuilder.CommentSaver.init(commentRepository);
    }
}
