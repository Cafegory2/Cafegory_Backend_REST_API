package com.example.demo.dto.study;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.implement.study.CafeStudyTagType;
import com.example.demo.study.domain.MemberComms;
import com.example.demo.implement.study.RecruitmentStatus;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.study.infrastructure.CafeStudyEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeStudySearchListResponse {

	private CafeStudyInfo cafeStudyInfo;
	private WriterInfo writerInfo;
	private CafeInfo cafeInfo;

	public static CafeStudySearchListResponse from(CafeStudyEntity cafeStudy) {
		CafeStudySearchListResponse response = new CafeStudySearchListResponse();
		response.cafeStudyInfo = createCafeStudyInfo(cafeStudy);
		response.writerInfo = createWriterInfo(cafeStudy);
		response.cafeInfo = createCafeInfo(cafeStudy);

		return response;
	}

	private static CafeInfo createCafeInfo(CafeStudyEntity cafeStudy) {
		CafeEntity cafe = cafeStudy.getCafe();

		return CafeInfo.builder()
			.id(cafe.getId())
			.imgUrl(cafe.getMainImageUrl())
			.name(cafe.getName())
			.build();
	}

	private static WriterInfo createWriterInfo(CafeStudyEntity cafeStudy) {
		MemberEntity writer = cafeStudy.getCoordinator();

		return WriterInfo.builder()
			.id(writer.getId())
			.nickname(writer.getNickname())
			.build();
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
