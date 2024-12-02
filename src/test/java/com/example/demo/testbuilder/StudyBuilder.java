package com.example.demo.testbuilder;

import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.study.domain.MemberComms;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.study.infrastructure.CafeStudyRepository;
import com.example.demo.study.infrastructure.StudyPeriod;

import java.time.LocalDateTime;

import static com.example.demo.testbuilder.CafeBuilder.aCafe;
import static com.example.demo.testbuilder.MemberBuilder.*;

public class StudyBuilder {

    private String name = "테스트 카공 이름";
    private CafeEntity cafe = aCafe().build();
    private MemberEntity coordinator = aMember().build();
    private StudyPeriod studyPeriod = StudyPeriod.builder()
        .startDateTime(LocalDateTime.of(2999, 1, 1, 12, 0))
        .endDateTime(LocalDateTime.of(2999, 1, 1, 14, 0)).build();
    private MemberComms memberComms = MemberComms.WELCOME;
    private int maxParticipants = 6;
    private String introduction = "테스트 카공 소개글";

    private StudyBuilder() {}

    private StudyBuilder(StudyBuilder copy) {
        this.name = copy.name;
        this.cafe = copy.cafe;
        this.coordinator = copy.coordinator;
        this.studyPeriod = copy.studyPeriod;
        this.memberComms = copy.memberComms;
        this.maxParticipants = copy.maxParticipants;
        this.introduction = copy.introduction;
    }

    private StudyBuilder but() {
        return new StudyBuilder(this);
    }

    public static StudyBuilder aStudy() {
        return new StudyBuilder();
    }

    public StudyBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public StudyBuilder with(CafeEntity cafeEntity) {
        this.cafe = cafeEntity;
        return this;
    }

    public StudyBuilder with(MemberEntity memberEntity) {
        this.coordinator = memberEntity;
        return this;
    }

    public StudyBuilder withStudyPeriod(LocalDateTime start, LocalDateTime end) {
        this.studyPeriod = StudyPeriod.builder().startDateTime(start).endDateTime(end).build();
        return this;
    }

    public StudyBuilder withMemberComms(MemberComms memberComms) {
        this.memberComms = memberComms;
        return this;
    }

    public StudyBuilder withMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
        return this;
    }

    public StudyBuilder withIntroduction(String introduction) {
        this.introduction = introduction;
        return this;
    }

    public CafeStudyEntity build() {
        return CafeStudyEntity.builder()
            .name(name)
            .cafe(cafe)
            .coordinator(coordinator)
            .studyPeriod(studyPeriod)
            .memberComms(memberComms)
            .maxParticipants(maxParticipants)
            .introduction(introduction)
            .build();
    }

    public CafeStudyEntity save() {
        return StudySaver.studyRepository.save(build());
    }

    public static class StudySaver {
        static CafeStudyRepository studyRepository;

        public static void init(CafeStudyRepository studyRepo) {
            studyRepository = studyRepo;
        }
    }
}
