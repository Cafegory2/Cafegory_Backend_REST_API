package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.PagedResponse;
import com.example.demo.dto.StudyOnceCreateRequest;
import com.example.demo.dto.StudyOnceSearchRequest;
import com.example.demo.dto.StudyOnceSearchResponse;
import com.example.demo.dto.UpdateAttendanceResponse;

public interface StudyOnceService {
	void tryJoin(long memberIdThatExpectedToJoin, long studyId);

	void tryQuit(long memberIdThatExpectedToQuit, long studyId);

	void tryCancel(long memberIdThatExpectedToCancel, long studyId);

	PagedResponse<StudyOnceSearchResponse> searchStudy(StudyOnceSearchRequest studyOnceSearchRequest);

	StudyOnceSearchResponse searchByStudyId(long studyId);

	List<UpdateAttendanceResponse> updateAttendance(long leaderId, long memberId, boolean attendance);

	StudyOnceSearchResponse createStudy(long leaderId, StudyOnceCreateRequest studyOnceCreateRequest);
}
