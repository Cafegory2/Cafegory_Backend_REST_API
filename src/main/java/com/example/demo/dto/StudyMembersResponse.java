package com.example.demo.dto;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StudyMembersResponse {

	private final List<MemberResponse> joinedMembers;

}
