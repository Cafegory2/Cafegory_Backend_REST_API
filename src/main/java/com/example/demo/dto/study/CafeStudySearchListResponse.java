package com.example.demo.dto.study;

import com.example.demo.implement.cafe.Cafe;
import com.example.demo.implement.member.Member;
import com.example.demo.implement.study.*;
import com.example.demo.packageex.cafestudy.repository.CafeStudyEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeStudySearchListResponse {

    private CafeStudyInfo cafeStudyInfo;
    private WriterInfo writerInfo;
    private CafeInfo cafeInfo;

    public static CafeStudySearchListResponse from(CafeStudyEntity cafeStudyEntity) {
        CafeStudySearchListResponse response = new CafeStudySearchListResponse();
        response.cafeStudyInfo = createCafeStudyInfo(cafeStudyEntity);
        response.writerInfo = createWriterInfo(cafeStudyEntity);
        response.cafeInfo = createCafeInfo(cafeStudyEntity);

        return response;
    }

    private static CafeInfo createCafeInfo(CafeStudyEntity cafeStudyEntity) {
        Cafe cafe = cafeStudyEntity.getCafe();

        return CafeInfo.builder()
            .id(cafe.getId())
            .imgUrl(cafe.getMainImageUrl())
            .name(cafe.getName())
            .build();
    }

    private static WriterInfo createWriterInfo(CafeStudyEntity cafeStudyEntity) {
        Member writer = cafeStudyEntity.getCoordinator();

        return WriterInfo.builder()
            .id(writer.getId())
            .nickname(writer.getNickname())
            .build();
    }

    private static CafeStudyInfo createCafeStudyInfo(CafeStudyEntity cafeStudyEntity) {
        return CafeStudyInfo.builder()
            .id(cafeStudyEntity.getId())
            .name(cafeStudyEntity.getName())
            .tags(
                cafeStudyEntity.getCafeStudyCafeStudyTags().stream()
                    .map(cafeStudyCafeStudyTag -> cafeStudyCafeStudyTag.getCafeStudyTag().getType())
                    .collect(Collectors.toList())
            )
            .startDateTime(cafeStudyEntity.getStudyPeriod().getStartDateTime())
            .endDateTime(cafeStudyEntity.getStudyPeriod().getEndDateTime())
            .maximumParticipants(cafeStudyEntity.getMaxParticipants())
            .currentParticipants(cafeStudyEntity.getCafeStudyMembers().size())
            .views(cafeStudyEntity.getViews())
            .memberComms(cafeStudyEntity.getMemberComms())
            .recruitmentStatus(cafeStudyEntity.getRecruitmentStatus())
            .build();
    }

    @Getter
    @Setter
    @Builder
    private static class CafeStudyInfo {

        private Long id;
        private String name;
        private List<CafeStudyTagType> tags;
        private LocalDateTime startDateTime;
        private LocalDateTime endDateTime;
        private int maximumParticipants;
        private int currentParticipants;
        private int views;
        private MemberComms memberComms;
        private RecruitmentStatus recruitmentStatus;
    }

    @Getter
    @Setter
    @Builder
    private static class WriterInfo {

        private Long id;
        private String nickname;
    }

    @Getter
    @Setter
    @Builder
    private static class CafeInfo {

        private Long id;
        private String imgUrl;
        private String name;
    }
}
