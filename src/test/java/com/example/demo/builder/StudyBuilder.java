package com.example.demo.builder;

import com.example.demo.domain.DateAudit;
import com.example.demo.study.domain.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.example.demo.builder.CoordinatorBuilder.*;

public class StudyBuilder {

    private Long id = 1L;
    private String name = "테스트 스터디";
    private Long cafeId = 1L;
    private Coordinator coordinator = aCoordinator().build();
    private Schedule schedule = Schedule.builder()
            .startDateTime(LocalDateTime.of(2999, 1, 1, 10, 0))
            .endDateTime(LocalDateTime.of(2999, 1, 1, 12, 0)).build();
    private MemberComms memberComms = MemberComms.WELCOME;
    private int maxParticipantCount = 6;
    private String introduction = "테스트 소개";
    private RecruitmentStatus recruitmentStatus = RecruitmentStatus.OPEN;
    private List<CafeStudyTagType> tags = Arrays.asList(CafeStudyTagType.DEVELOPMENT);

    private DateAudit dateAudit = DateAuditBuilder.aDateAudit().build();

    private StudyBuilder() {}

    private StudyBuilder(StudyBuilder copy) {
        this.id = copy.id;
        this.name = copy.name;
        this.cafeId = copy.cafeId;
        this.coordinator = copy.coordinator;
        this.schedule = copy.schedule;
        this.memberComms = copy.memberComms;
        this.maxParticipantCount = copy.maxParticipantCount;
        this.introduction = copy.introduction;
        this.recruitmentStatus = copy.recruitmentStatus;
        this.tags = copy.tags;
        this.dateAudit = copy.dateAudit;
    }

    public StudyBuilder but() {
        return new StudyBuilder(this);
    }

    public static StudyBuilder aStudy() {
        return new StudyBuilder();
    }

    public StudyBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public StudyBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public StudyBuilder withCafeId(Long cafeId) {
        this.cafeId = cafeId;
        return this;
    }

    public StudyBuilder with(CoordinatorBuilder coordinatorBuilder) {
        this.coordinator = coordinatorBuilder.build();
        return this;
    }

    public StudyBuilder withSchedule(Schedule schedule) {
        this.schedule = schedule;
        return this;
    }

    public StudyBuilder withMemberComms(MemberComms memberComms) {
        this.memberComms = memberComms;
        return this;
    }

    public StudyBuilder withMaxParticipantCount(int maxParticipantCount) {
        this.maxParticipantCount = maxParticipantCount;
        return this;
    }

    public StudyBuilder withIntroduction(String introduction) {
        this.introduction = introduction;
        return this;
    }

    public StudyBuilder withRecruitmentStatus(RecruitmentStatus recruitmentStatus) {
        this.recruitmentStatus = recruitmentStatus;
        return this;
    }

    public StudyBuilder withTags(CafeStudyTagType... studyTagTypes) {
        this.tags = List.of(studyTagTypes);
        return this;
    }

    public StudyBuilder withDateAudit(DateAudit dateAudit) {
        this.dateAudit = dateAudit;
        return this;
    }

    public Study build() {
        return Study.builder()
                .id(this.id)
                .name(this.name)
                .cafeId(this.cafeId)
                .coordinator(this.coordinator)
                .schedule(this.schedule)
                .memberComms(this.memberComms)
                .maxParticipantCount(this.maxParticipantCount)
                .introduction(this.introduction)
                .recruitmentStatus(this.recruitmentStatus)
                .tags(this.tags)
                .dateAudit(this.dateAudit)
                .build();
    }
}
