package com.example.demo.dto;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateAttendanceResponse {

	private final List<StudyMemberStateResponse> states;

}
