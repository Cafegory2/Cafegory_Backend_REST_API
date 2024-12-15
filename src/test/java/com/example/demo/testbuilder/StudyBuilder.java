package com.example.demo.testbuilder;

import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.implement.study.CafeStudyTagEntity;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.study.domain.MemberComms;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.study.infrastructure.CafeStudyRepository;
import com.example.demo.study.infrastructure.StudyPeriod;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.testbuilder.CafeBuilder.aCafe;
import static com.example.demo.testbuilder.MemberBuilder.*;
import static com.example.demo.testbuilder.StudyStudyTagBuilder.*;

public class StudyBuilder {

    private String name = "테스트 카공 이름";
    private CafeEntity cafe = aCafe().build();
    private MemberEntity coordinator = aMember().build();
    private StudyPeriod studyPeriod = StudyPeriod.builder()
            .startDateTime(LocalDateTime.of(2999, 1, 1, 10, 0))
            .endDateTime(LocalDateTime.of(2999, 1, 1, 12, 0)).build();
    private MemberComms memberComms = MemberComms.WELCOME;
    private int maxParticipants = 6;
    private String introduction = "테스트 카공 소개글";

    private List<CafeStudyTagEntity> studyTags = new ArrayList<>();

    private StudyBuilder() {}

    private StudyBuilder(StudyBuilder copy) {
        this.name = copy.name;
        this.cafe = copy.cafe;
        this.coordinator = copy.coordinator;
        this.studyPeriod = copy.studyPeriod;
        this.memberComms = copy.memberComms;
        this.maxParticipants = copy.maxParticipants;
        this.introduction = copy.introduction;
        this.studyTags = copy.studyTags;
    }

    public StudyBuilder but() {
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

    public StudyBuilder shiftDays(int days) {
        LocalDateTime start = this.studyPeriod.getStartDateTime().plusDays(1);
        LocalDateTime end = this.studyPeriod.getEndDateTime().plusDays(1);

        this.studyPeriod = StudyPeriod.builder().startDateTime(start).endDateTime(end).build();
        return this;
    }

    public StudyBuilder withStudyPeriodFrom10To12() {
        LocalDateTime start = LocalDateTime.of(2000, 1, 1, 10, 0, 0);
        LocalDateTime end = LocalDateTime.of(2000, 1, 1, 12, 0, 0);

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

    public StudyBuilder with(CafeStudyTagEntity... studyTags) {
        this.studyTags.addAll(List.of(studyTags));
        return this;
    }

    public CafeStudyEntity build() {
        return CafeStudyEntity.builder()
                .name(this.name)
                .cafe(this.cafe)
                .coordinator(this.coordinator)
                .studyPeriod(this.studyPeriod)
                .memberComms(this.memberComms)
                .maxParticipants(this.maxParticipants)
                .introduction(this.introduction)
                .build();
    }

    public static class StudySaver {
        private static CafeStudyRepository studyRepository;

        public static void init(CafeStudyRepository studyRepo) {
            studyRepository = studyRepo;
        }
    }

    public CafeStudyEntity save() {
        CafeStudyEntity study = StudySaver.studyRepository.save(build());
        studyTags.forEach(studyTag -> aStudyStudyTag().with(study).with(studyTag).save());

        return study;
    }
}