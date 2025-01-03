package com.example.demo.study.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.demo.domain.DateAudit;
import com.example.demo.study.domain.*;
import org.hibernate.annotations.Where;

import com.example.demo.cafe.domain.CafeId;
import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.member.domain.MemberId;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.trash.implement.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "deleted_date IS NULL")
@Table(name = "cafe_study")
public class CafeStudyEntity extends BaseEntity {

    public static final int MIN_DELAY_BEFORE_START = 1 * 60 * 60;

    @Id
    @GeneratedValue
    @Column(name = "cafe_study_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CafeEntity cafe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coordinator_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private MemberEntity coordinator;

    @Embedded
    private StudyPeriod studyPeriod;

    @Enumerated(EnumType.STRING)
    private MemberComms memberComms;

    private int maxParticipants;
    private String introduction;
    private int views;

    @Enumerated(EnumType.STRING)
    private RecruitmentStatus recruitmentStatus;

    @OneToMany(mappedBy = "cafeStudy")
    private List<CafeStudyMemberEntity> cafeStudyMembers = new ArrayList<>();

    @OneToMany(mappedBy = "cafeStudy")
    private List<CafeStudyCafeStudyTagEntity> cafeStudyCafeStudyTags = new ArrayList<>();

    public CafeStudyEntity(Long studyId) {
        this.id = studyId;
    }

    public CafeStudyEntity(StudyContent content, Long cafeId, Long memberId) {
        this.name = content.getName();
        this.cafe = new CafeEntity(cafeId);
        this.coordinator = new MemberEntity(memberId);
        this.studyPeriod = StudyPeriod.builder()
                .startDateTime(content.getStartDateTime())
                .endDateTime(content.getEndDateTime())
                .build();
        this.memberComms = content.getMemberComms();
        this.maxParticipants = content.getMaxParticipantCount();
        this.introduction = content.getIntroduction();
        this.views = 0;
        this.recruitmentStatus = RecruitmentStatus.OPEN;
    }

    @Builder
    private CafeStudyEntity(String name, CafeEntity cafe, MemberEntity coordinator, StudyPeriod studyPeriod,
                            MemberComms memberComms, int maxParticipants, String introduction, List<CafeStudyCafeStudyTagEntity> tags) {
        this.name = name;
        this.cafe = cafe;
        this.coordinator = coordinator;
        this.studyPeriod = studyPeriod;
        this.memberComms = memberComms;
        this.maxParticipants = maxParticipants;
        this.introduction = introduction;
        this.views = 0;
        this.recruitmentStatus = RecruitmentStatus.OPEN;
        this.cafeStudyCafeStudyTags = tags;

        addCoordinatorToStudy(coordinator);
    }

    public Study toStudy() {
        return Study.builder()
                .id(new StudyId(this.id))
                .content(
                        StudyContent.builder()
                                .name(this.name)
                                .schedule(
                                        Schedule.builder()
                                                .startDateTime(this.getStudyPeriod().getStartDateTime())
                                                .endDateTime(this.getStudyPeriod().getEndDateTime())
                                                .build()
                                )
                                .memberComms(this.memberComms)
                                .maxParticipantCount(this.maxParticipants)
                                .introduction(this.introduction)
                                .tags(this.cafeStudyCafeStudyTags.stream()
                                        .map(cafeTag -> cafeTag.getCafeStudyTag().getType())
                                        .collect(Collectors.toList()))
                                .build()

                )
                .cafeId(new CafeId(this.cafe.getId()))
                .coordinator(
                        Coordinator.builder()
                                .id(new MemberId(this.coordinator.getId()))
                                .nickname(this.coordinator.getNickname())
                                .build())
                .recruitmentStatus(this.recruitmentStatus)
                .dateAudit(
                        DateAudit.builder()
                                .createdDate(this.getCreatedDate())
                                .modifiedDate(this.getLastModifiedDate())
                                .build()
                )
                .build();
    }

    public ViewCount toViewCount() {
        return ViewCount.builder()
                .totalViews(getViews())
                .build();
    }

    private void addCoordinatorToStudy(MemberEntity coordinator) {
        CafeStudyMemberEntity cafeStudyMember = CafeStudyMemberEntity.builder()
                .cafeStudy(this)
                .member(coordinator)
                .studyRole(StudyRole.COORDINATOR)
                .build();
        cafeStudyMembers.add(cafeStudyMember);
    }

}
