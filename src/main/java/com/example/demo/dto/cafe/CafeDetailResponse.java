package com.example.demo.dto.cafe;

import com.example.demo.implement.cafe.BusinessHourEntity;
import com.example.demo.implement.cafe.CafeEntity;
import com.example.demo.implement.cafe.MenuEntity;
import com.example.demo.implement.study.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeDetailResponse {

    private CafeInfo cafeInfo;
    private List<MenuInfo> menusInfo = new ArrayList<>();
    private List<CafeStudyInfo> openCafeStudiesInfo = new ArrayList<>();
    private List<CafeStudyInfo> closeCafeStudiesInfo = new ArrayList<>();

    public static CafeDetailResponse of(CafeEntity cafeEntity, BusinessHourEntity businessHourEntity, boolean isOpen,
                                        List<CafeStudyEntity> openCafeStudies, List<CafeStudyEntity> closeCafeStudies) {
        CafeDetailResponse response = new CafeDetailResponse();
        response.cafeInfo = createCafeInfo(cafeEntity, businessHourEntity, isOpen);
        response.menusInfo = createMenusInfo(cafeEntity);
        response.openCafeStudiesInfo = createCafeStudiesInfo(openCafeStudies);
        response.closeCafeStudiesInfo = createCafeStudiesInfo(closeCafeStudies);

        return response;
    }

    private static List<CafeStudyInfo> createCafeStudiesInfo(List<CafeStudyEntity> cafeStudies) {
        return cafeStudies.stream()
            .map(CafeDetailResponse::createCafeStudyInfo)
            .collect(Collectors.toList());
    }

    private static CafeStudyInfo createCafeStudyInfo(CafeStudyEntity cafeStudy) {
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
            .writer(cafeStudy.getCoordinator().getNickname())
            .build();
    }

    private static List<MenuInfo> createMenusInfo(CafeEntity cafeEntity) {
        List<MenuEntity> menus = cafeEntity.getMenus();

        return menus.stream()
            .map(CafeDetailResponse::createMenuInfo)
            .collect(Collectors.toList());
    }

    private static MenuInfo createMenuInfo(MenuEntity menu) {
        return MenuInfo.builder()
            .name(menu.getName())
            .price(menu.getPrice())
            .build();
    }

    private static CafeInfo createCafeInfo(CafeEntity cafeEntity, BusinessHourEntity businessHourEntity, boolean isOpen) {
        return CafeInfo.builder()
            .id(cafeEntity.getId())
            .name(cafeEntity.getName())
            .imgUrl(cafeEntity.getMainImageUrl())
            .address(cafeEntity.getAddress().getFullAddress())
            .openingTime(businessHourEntity.getOpeningTime())
            .closingTime(businessHourEntity.getClosingTime())
            .isOpen(isOpen)
            .sns(cafeEntity.getSns())
            .tags(
                cafeEntity.getCafeCafeTags().stream()
                    .map(cafeCafeTag -> cafeCafeTag.getCafeTag().getType())
                    .collect(Collectors.toList())
            )
            .build();
    }

    @Getter
    @Setter
    @Builder
    private static class CafeInfo {

        private Long id;
        private String name;
        private String imgUrl;
        private String address;
        private LocalTime openingTime;
        private LocalTime closingTime;
        private boolean isOpen;
        private String sns;
        private List<CafeTagType> tags;

    }

    @Getter
    @Setter
    @Builder
    private static class MenuInfo {

        private String name;
        private String price;
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

        private String writer;
    }

}
