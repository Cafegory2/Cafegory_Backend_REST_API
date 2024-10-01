package com.example.demo.dto.study;

import com.example.demo.implement.study.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeStudySearchListResponse {

    private CafeStudyInfo cafeStudyInfo;

    public static CafeStudySearchListResponse from(CafeStudy cafeStudy) {
        CafeStudySearchListResponse response = new CafeStudySearchListResponse();

        response.cafeStudyInfo = CafeStudyInfo.builder()
            .id(cafeStudy.getId())
            .name(cafeStudy.getName())
            .tags(
                cafeStudy.getCafeStudyCafeStudyTags().stream()
                    .map(cafeStudyCafeStudyTag -> cafeStudyCafeStudyTag.getCafeStudyTag().getType())
                    .collect(Collectors.toList())
            )
            .startDateTime(cafeStudy.getStudyPeriod().getStartDateTime())
            .endDateTime(cafeStudy.getStudyPeriod().getEndDateTime())
            .maxParticipants(cafeStudy.getMaxParticipants())
            .views(cafeStudy.getViews())
            .memberComms(cafeStudy.getMemberComms())
            .recruitmentStatus(cafeStudy.getRecruitmentStatus())
            .build();

        return response;
    }

    @Getter
    @Builder
    protected static class CafeStudyInfo {

        private Long id;
        private String name;
        private List<CafeStudyTagType> tags;
        private LocalDateTime startDateTime;
        private LocalDateTime endDateTime;
        private int maxParticipants;
        private int views;
        private MemberComms memberComms;
        private RecruitmentStatus recruitmentStatus;
    }
}
