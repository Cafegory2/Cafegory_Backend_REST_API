package com.example.demo.service.study;

import com.example.demo.dto.study.CafeStudyCreateRequest;
import com.example.demo.dto.study.CafeStudyCreateResponse;

public interface CafeStudyService {
	// void tryJoin(long memberIdThatExpectedToJoin, long studyId);
	//
	// void tryQuit(long memberIdThatExpectedToQuit, long studyId);
	//
	// PagedResponse<StudyOnceSearchListResponse> searchStudy(StudyOnceSearchRequest studyOnceSearchRequest);
	//
	// StudyOnceSearchResponse searchByStudyId(long studyId);
	//
	// StudyOnceSearchResponse searchStudyOnceWithMemberParticipation(long studyOnceId, long memberId);
	//
	// UpdateAttendanceResponse updateAttendances(long leaderId, long studyOnceId,
	// 	UpdateAttendanceRequest request, LocalDateTime now);
	//
	// void updateAttendance(long leaderId, long studyOnceId, long memberId, Attendance attendance, LocalDateTime now);

	CafeStudyCreateResponse createStudy(long coordinatorId, CafeStudyCreateRequest cafeStudyCreateRequest);

	// Long changeCafe(Long requestMemberId, Long studyOnceId, Long changingCafeId);
	//
	// StudyMemberListResponse findStudyMembersById(Long studyOnceId);
	//
	// StudyOnceResponse findStudyOnce(Long studyOnceId, LocalDateTime now);
	//
	// boolean doesOnlyStudyLeaderExist(Long studyOnceId);
	//
	// boolean isStudyOnceLeader(Long memberId, Long studyOnceId);
	//
	// void updateStudyOnce(long requestedMemberId, long studyOnceId, StudyOnceUpdateRequest request, LocalDateTime now);
	//
	// void updateStudyOncePartially(long requestedMemberId, long studyOnceId, StudyOnceUpdateRequest request);
}
