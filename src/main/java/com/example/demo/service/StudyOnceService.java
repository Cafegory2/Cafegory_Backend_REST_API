package com.example.demo.service;

import java.time.LocalDateTime;

import com.example.demo.domain.Attendance;
import com.example.demo.dto.PagedResponse;
import com.example.demo.dto.StudyOnceCreateRequest;
import com.example.demo.dto.StudyOnceSearchRequest;
import com.example.demo.dto.StudyOnceSearchResponse;
import com.example.demo.dto.UpdateAttendanceRequest;
import com.example.demo.dto.UpdateAttendanceResponse;

public interface StudyOnceService {
	void tryJoin(long memberIdThatExpectedToJoin, long studyId);

	void tryQuit(long memberIdThatExpectedToQuit, long studyId);

	PagedResponse<StudyOnceSearchResponse> searchStudy(StudyOnceSearchRequest studyOnceSearchRequest);

	StudyOnceSearchResponse searchByStudyId(long studyId);

	UpdateAttendanceResponse updateAttendances(long leaderId, long studyOnceId,
		UpdateAttendanceRequest request, LocalDateTime now);

	void updateAttendance(long leaderId, long studyOnceId, long memberId, Attendance attendance, LocalDateTime now);

	StudyOnceSearchResponse createStudy(long leaderId, StudyOnceCreateRequest studyOnceCreateRequest);
}
