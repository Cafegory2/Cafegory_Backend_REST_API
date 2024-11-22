package com.example.demo.cafe.presentation;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.cafe.domain.Cafe;
import com.example.demo.cafe.domain.CafeTagType;
import com.example.demo.cafe.infrastructure.BusinessHourEntity;
import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.cafe.infrastructure.MenuEntity;
import com.example.demo.study.domain.CafeStudyTagType;
import com.example.demo.study.domain.MemberComms;
import com.example.demo.study.domain.RecruitmentStatus;
import com.example.demo.study.infrastructure.CafeStudyEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeDetailResponse {

	private CafeInfo cafeInfo;
	private List<MenuInfo> menusInfo = new ArrayList<>();
	private List<CafeStudyInfo> openCafeStudiesInfo = new ArrayList<>();
	private List<CafeStudyInfo> closeCafeStudiesInfo = new ArrayList<>();

	//TODO 11월 22일 createCafeInfo(cafe) 하다가 중단
	public static CafeDetailResponse of(Cafe cafe, CafeEntity cafeEntity, BusinessHourEntity businessHourEntity, boolean isOpen,
										List<CafeStudyEntity> openCafeStudies, List<CafeStudyEntity> closeCafeStudies) {
		CafeDetailResponse response = new CafeDetailResponse();
		response.cafeInfo = createCafeInfo(cafe, businessHourEntity, isOpen);
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

	private static CafeInfo createCafeInfo(Cafe cafe, BusinessHourEntity businessHourEntity,
		boolean isOpen) {
		return CafeInfo.builder()
			.id(cafe.getId())
			.name(cafe.getName())
			.imgUrl(cafe.getImgUrl())
			.address(cafe.getAddress().getFullAddress())
			.openingTime(businessHourEntity.getOpeningTime())
			.closingTime(businessHourEntity.getClosingTime())
			.isOpen(isOpen)
			.sns(cafe.getSns())
			.tags(
				cafe.getCafeTagTypes()
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
