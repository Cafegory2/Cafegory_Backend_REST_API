package com.example.demo.dto.study;

import com.example.demo.implement.cafe.Cafe;
import com.example.demo.implement.member.Member;
import com.example.demo.implement.study.*;
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

    public static CafeStudySearchListResponse from(CafeStudy cafeStudy) {
        CafeStudySearchListResponse response = new CafeStudySearchListResponse();
        response.cafeStudyInfo = createCafeStudyInfo(cafeStudy);
        response.writerInfo = createWriterInfo(cafeStudy);
        response.cafeInfo = createCafeInfo(cafeStudy);

        return response;
    }

    private static CafeInfo createCafeInfo(CafeStudy cafeStudy) {
        Cafe cafe = cafeStudy.getCafe();

        return CafeInfo.builder()
            .id(cafe.getId())
            .imgUrl(cafe.getMainImageUrl())
            .name(cafe.getName())
            .build();
    }

    private static WriterInfo createWriterInfo(CafeStudy cafeStudy) {
        Member writer = cafeStudy.getCoordinator();

        return WriterInfo.builder()
            .id(writer.getId())
            .nickname(writer.getNickname())
            .build();
    }

    private static CafeStudyInfo createCafeStudyInfo(CafeStudy cafeStudy) {
        return CafeStudyInfo.builder()
            .id(cafeStudy.getId())
            .name(cafeStudy.getName())
            .tags(
                cafeStudy.getCafeStudyCafeStudyTags().stream()
                    .map(cafeStudyCafeStudyTag -> cafeStudyCafeStudyTag.getCafeStudyTag().getType())
                    .collect(Collectors.toList())
            )
            .startDateTime(cafeStudy.getStudyPeriod().getStartDateTime())
            .endDateTime(cafeStudy.getStudyPeriod().getEndDateTime())
            .maximumParticipants(cafeStudy.getMaxParticipants())
            .currentParticipants(cafeStudy.getCafeStudyMembers().size())
            .views(cafeStudy.getViews())
            .memberComms(cafeStudy.getMemberComms())
            .recruitmentStatus(cafeStudy.getRecruitmentStatus())
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
