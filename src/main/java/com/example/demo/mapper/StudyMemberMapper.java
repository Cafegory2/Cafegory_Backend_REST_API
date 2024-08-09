// package com.example.demo.mapper;
//
// import java.util.List;
// import java.util.stream.Collectors;
//
// import com.example.demo.domain.study.CafeStudyMember;
// import com.example.demo.dto.member.MemberResponse;
//
// public class StudyMemberMapper {
//
// 	public List<MemberResponse> toMemberResponses(List<CafeStudyMember> cafeStudyMembers) {
// 		return cafeStudyMembers.stream()
// 			.map(studyMember -> new MemberResponse(
// 				studyMember.getMember().getId(),
// 				studyMember.getMember().getName(),
// 				studyMember.getMember().getThumbnailImage().getThumbnailImage()))
// 			.collect(Collectors.toList());
// 	}
//
// }
