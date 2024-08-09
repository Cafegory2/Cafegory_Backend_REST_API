// package com.example.demo.service.study;
//
// import static com.example.demo.exception.ExceptionType.*;
// import static com.example.demo.util.TruncatedTimeUtil.*;
//
// import java.time.Duration;
// import java.time.LocalDateTime;
// import java.time.LocalTime;
// import java.util.List;
// import java.util.stream.Collectors;
//
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
//
// import com.example.demo.domain.cafe.BusinessHour;
// import com.example.demo.domain.cafe.BusinessHourOpenChecker;
// import com.example.demo.domain.cafe.Cafe;
// import com.example.demo.domain.member.Member;
// import com.example.demo.domain.study.Attendance;
// import com.example.demo.domain.study.CafeStudy;
// import com.example.demo.domain.study.CafeStudyMember;
// import com.example.demo.domain.study.StudyMemberId;
// import com.example.demo.dto.PagedResponse;
// import com.example.demo.dto.member.MemberResponse;
// import com.example.demo.dto.study.StudyMemberListResponse;
// import com.example.demo.dto.study.StudyMemberStateRequest;
// import com.example.demo.dto.study.StudyMemberStateResponse;
// import com.example.demo.dto.study.StudyOnceCreateRequest;
// import com.example.demo.dto.study.StudyOnceCreateResponse;
// import com.example.demo.dto.study.StudyOnceResponse;
// import com.example.demo.dto.study.StudyOnceSearchListResponse;
// import com.example.demo.dto.study.StudyOnceSearchRequest;
// import com.example.demo.dto.study.StudyOnceSearchResponse;
// import com.example.demo.dto.study.StudyOnceUpdateRequest;
// import com.example.demo.dto.study.UpdateAttendanceRequest;
// import com.example.demo.dto.study.UpdateAttendanceResponse;
// import com.example.demo.exception.CafegoryException;
// import com.example.demo.mapper.StudyMemberMapper;
// import com.example.demo.mapper.StudyOnceMapper;
// import com.example.demo.repository.cafe.CafeRepository;
// import com.example.demo.repository.member.MemberRepository;
// import com.example.demo.repository.study.StudyMemberRepository;
// import com.example.demo.repository.study.StudyOnceRepository;
//
// import lombok.RequiredArgsConstructor;
//
// @Service
// @RequiredArgsConstructor
// @Transactional
// public class StudyOnceServiceImpl implements StudyOnceService {
//
// 	private final CafeRepository cafeRepository;
// 	private final StudyOnceRepository studyOnceRepository;
// 	private final MemberRepository memberRepository;
// 	private final StudyMemberRepository studyMemberRepository;
// 	private final StudyOnceMapper studyOnceMapper;
// 	private final StudyMemberMapper studyMemberMapper;
// 	private final BusinessHourOpenChecker openChecker = new BusinessHourOpenChecker();
//
// 	@Override
// 	public void tryJoin(long memberId, long studyId) {
// 		CafeStudy cafeStudy = studyOnceRepository.findById(studyId)
// 			.orElseThrow(() -> new CafegoryException(STUDY_ONCE_NOT_FOUND));
// 		Member member = findMemberById(memberId);
// 		cafeStudy.tryJoin(member, LocalDateTime.now());
// 	}
//
// 	@Override
// 	public void tryQuit(long memberIdThatExpectedToQuit, long studyId) {
// 		Member member = memberRepository.findById(memberIdThatExpectedToQuit)
// 			.orElseThrow(() -> new CafegoryException(MEMBER_NOT_FOUND));
// 		CafeStudy cafeStudy = studyOnceRepository.findById(studyId)
// 			.orElseThrow(() -> new CafegoryException(STUDY_ONCE_NOT_FOUND));
// 		if (cafeStudy.getLeader().equals(member)) {
// 			deleteStudy(cafeStudy);
// 		}
// 		CafeStudyMember needToRemoveCafeStudyMember = cafeStudy.tryQuit(member, LocalDateTime.now());
// 		studyMemberRepository.delete(needToRemoveCafeStudyMember);
// 	}
//
// 	private void deleteStudy(CafeStudy cafeStudy) {
// 		if (cafeStudy.getStudyMembers().size() > 1) {
// 			throw new CafegoryException(STUDY_ONCE_LEADER_QUIT_FAIL);
// 		}
// 		studyOnceRepository.delete(cafeStudy);
// 	}
//
// 	@Override
// 	public PagedResponse<StudyOnceSearchListResponse> searchStudy(StudyOnceSearchRequest studyOnceSearchRequest) {
// 		int totalCount = Math.toIntExact(studyOnceRepository.count(studyOnceSearchRequest));
// 		int sizePerPage = studyOnceSearchRequest.getSizePerPage();
// 		int maxPage = calculateMaxPage(totalCount, sizePerPage);
// 		List<CafeStudy> allByCafeStudySearchRequest = studyOnceRepository.findAllByStudyOnceSearchRequest(
// 			studyOnceSearchRequest);
// 		List<StudyOnceSearchListResponse> searchResults = allByCafeStudySearchRequest
// 			.stream()
// 			.map(studyOnce -> studyOnceMapper.toStudyOnceSearchListResponse(studyOnce,
// 				studyOnce.canJoin(LocalDateTime.now())))
// 			.collect(Collectors.toList());
// 		return new PagedResponse<>(studyOnceSearchRequest.getPage(), maxPage, searchResults.size(), totalCount,
// 			searchResults);
// 	}
//
// 	private int calculateMaxPage(int totalCount, int sizePerPage) {
// 		if (totalCount % sizePerPage == 0) {
// 			return totalCount / sizePerPage;
// 		}
// 		return totalCount / sizePerPage + 1;
// 	}
//
// 	@Override
// 	public StudyOnceSearchResponse searchByStudyId(long studyId) {
// 		CafeStudy searched = studyOnceRepository.findById(studyId)
// 			.orElseThrow(() -> new CafegoryException(STUDY_ONCE_NOT_FOUND));
// 		boolean canJoin = searched.canJoin(LocalDateTime.now());
// 		return studyOnceMapper.toStudyOnceSearchResponse(searched, canJoin, false);
// 	}
//
// 	@Override
// 	public StudyOnceSearchResponse searchStudyOnceWithMemberParticipation(long studyOnceId, long memberId) {
// 		CafeStudy searched = studyOnceRepository.findById(studyOnceId)
// 			.orElseThrow(() -> new CafegoryException(STUDY_ONCE_NOT_FOUND));
// 		boolean canJoin = searched.canJoin(LocalDateTime.now());
// 		boolean isAttendance = searched.isAttendance(findMemberById(memberId));
// 		return studyOnceMapper.toStudyOnceSearchResponse(searched, canJoin, isAttendance);
// 	}
//
// 	@Override
// 	public UpdateAttendanceResponse updateAttendances(long leaderId, long studyOnceId,
// 		UpdateAttendanceRequest request, LocalDateTime now) {
// 		processAttendanceUpdates(leaderId, studyOnceId, request, now);
//
// 		List<StudyMemberId> studyMemberIds = generateStudyMemberIdsFromRequest(studyOnceId, request);
// 		List<CafeStudyMember> cafeStudyMembers = studyMemberRepository.findAllById(studyMemberIds);
// 		return new UpdateAttendanceResponse(mapToStateResponses(cafeStudyMembers));
// 	}
//
// 	private List<StudyMemberStateResponse> mapToStateResponses(List<CafeStudyMember> cafeStudyMembers) {
// 		return cafeStudyMembers.stream()
// 			.map(studyMember -> new StudyMemberStateResponse(studyMember.getId().getMemberId(),
// 				studyMember.getAttendance().isPresent(), studyMember.getLastModifiedDate()))
// 			.collect(Collectors.toList());
// 	}
//
// 	private List<StudyMemberId> generateStudyMemberIdsFromRequest(long studyOnceId, UpdateAttendanceRequest request) {
// 		return request.getStates().stream()
// 			.map(memberReq -> new StudyMemberId(memberReq.getMemberId(), studyOnceId))
// 			.collect(Collectors.toList());
// 	}
//
// 	private void processAttendanceUpdates(long leaderId, long studyOnceId, UpdateAttendanceRequest request,
// 		LocalDateTime now) {
// 		for (StudyMemberStateRequest memberStateRequest : request.getStates()) {
// 			Attendance attendance = memberStateRequest.isAttendance() ? Attendance.YES : Attendance.NO;
// 			updateAttendance(leaderId, studyOnceId, memberStateRequest.getMemberId(), attendance, now);
// 		}
// 	}
//
// 	@Override
// 	public void updateAttendance(long leaderId, long studyOnceId, long memberId, Attendance attendance,
// 		LocalDateTime now) {
// 		LocalDateTime microNow = truncateDateTimeToSecond(now);
// 		CafeStudy searched = findStudyOnceById(studyOnceId);
//
// 		if (!studyOnceRepository.existsByLeaderId(leaderId)) {
// 			throw new CafegoryException(STUDY_ONCE_INVALID_LEADER);
// 		}
// 		validateEarlyToTakeAttendance(microNow, searched.getStartDateTime());
// 		validateLateToTakeAttendance(microNow, searched.getStartDateTime(), searched.getEndDateTime());
//
// 		CafeStudyMember findCafeStudyMember = studyMemberRepository.findById(new StudyMemberId(memberId, studyOnceId))
// 			.orElseThrow(() -> new CafegoryException(STUDY_MEMBER_NOT_FOUND));
// 		findCafeStudyMember.setAttendance(attendance);
// 		findCafeStudyMember.setLastModifiedDate(microNow);
// 	}
//
// 	private void validateLateToTakeAttendance(LocalDateTime now, LocalDateTime startDateTime,
// 		LocalDateTime endDateTime) {
// 		Duration halfDuration = Duration.between(startDateTime, endDateTime).dividedBy(2);
// 		LocalDateTime midTime = startDateTime.plus(halfDuration);
//
// 		if (now.isAfter(midTime)) {
// 			throw new CafegoryException(STUDY_ONCE_LATE_TAKE_ATTENDANCE);
// 		}
// 	}
//
// 	private void validateEarlyToTakeAttendance(LocalDateTime now, LocalDateTime startDateTime) {
// 		if (now.minusMinutes(10).isBefore(startDateTime)) {
// 			throw new CafegoryException(STUDY_ONCE_EARLY_TAKE_ATTENDANCE);
// 		}
// 	}
//
// 	private CafeStudy findStudyOnceById(long studyOnceId) {
// 		return studyOnceRepository.findById(studyOnceId)
// 			.orElseThrow(() -> new CafegoryException(STUDY_ONCE_NOT_FOUND));
// 	}
//
// 	@Override
// 	public StudyOnceCreateResponse createStudy(long leaderId, StudyOnceCreateRequest request) {
// 		Cafe cafe = findCafeById(request.getCafeId());
// 		BusinessHour businessHour = cafe.findBusinessHour(request.getStartDateTime().getDayOfWeek());
// 		validateBetweenBusinessHour(
// 			request.getStartDateTime().toLocalTime(),
// 			request.getEndDateTime().toLocalTime(),
// 			businessHour);
// 		Member leader = findMemberById(leaderId);
// 		validateStudyScheduleConflict(
// 			truncateDateTimeToSecond(request.getStartDateTime()),
// 			truncateDateTimeToSecond(request.getEndDateTime()),
// 			leader);
// 		CafeStudy cafeStudy = studyOnceMapper.toNewEntity(request, cafe, leader);
// 		CafeStudy saved = studyOnceRepository.save(cafeStudy);
// 		return studyOnceMapper.toStudyOnceCreateResponse(saved, true);
// 	}
//
// 	private void validateStudyScheduleConflict(LocalDateTime start, LocalDateTime end, Member leader) {
// 		boolean isScheduleConflict = leader.hasStudyScheduleConflict(start, end);
// 		if (isScheduleConflict) {
// 			throw new CafegoryException(STUDY_ONCE_CONFLICT_TIME);
// 		}
// 	}
//
// 	private void validateBetweenBusinessHour(LocalTime studyOnceStartTime, LocalTime studyOnceEndTime,
// 		BusinessHour businessHour) {
// 		boolean isBetweenBusinessHour = openChecker.checkBetweenBusinessHours(businessHour.getStartTime(),
// 			businessHour.getEndTime(), studyOnceStartTime, studyOnceEndTime);
// 		if (!isBetweenBusinessHour) {
// 			throw new CafegoryException(STUDY_ONCE_CREATE_BETWEEN_CAFE_BUSINESS_HOURS);
// 		}
// 	}
//
// 	@Override
// 	public void updateStudyOnce(long requestedMemberId, long studyOnceId, StudyOnceUpdateRequest request,
// 		LocalDateTime now) {
// 		CafeStudy cafeStudy = findStudyOnceById(studyOnceId);
// 		if (!isStudyOnceLeader(requestedMemberId, studyOnceId)) {
// 			throw new CafegoryException(STUDY_ONCE_LEADER_PERMISSION_DENIED);
// 		}
// 		if (request.getCafeId() != null) {
// 			cafeStudy.changeCafe(findCafeById(request.getCafeId()));
// 		}
// 		if (request.getName() != null) {
// 			cafeStudy.changeName(request.getName());
// 		}
// 		if (request.getStartDateTime() != null && request.getEndDateTime() != null) {
// 			Cafe cafe = cafeStudy.getCafe();
// 			validateBetweenBusinessHour(request.getStartDateTime().toLocalTime(),
// 				request.getEndDateTime().toLocalTime(),
// 				cafe.findBusinessHour(truncateDateTimeToSecond(now).getDayOfWeek()));
// 			cafeStudy.changeStudyOnceTime(request.getStartDateTime(), request.getEndDateTime());
// 		}
// 		if (request.getOpenChatUrl() != null) {
// 			cafeStudy.changeOpenChatUrl(request.getOpenChatUrl());
// 		}
// 		cafeStudy.changeMaxMemberCount(request.getMaxMemberCount());
// 		cafeStudy.changeCanTalk(request.isCanTalk());
// 	}
//
// 	@Override
// 	public void updateStudyOncePartially(long requestedMemberId, long studyOnceId, StudyOnceUpdateRequest request) {
// 		CafeStudy cafeStudy = findStudyOnceById(studyOnceId);
// 		if (!isStudyOnceLeader(requestedMemberId, studyOnceId)) {
// 			throw new CafegoryException(STUDY_ONCE_LEADER_PERMISSION_DENIED);
// 		}
// 		if (request.getCafeId() != null) {
// 			cafeStudy.changeCafe(findCafeById(request.getCafeId()));
// 		}
// 		cafeStudy.changeMaxMemberCount(request.getMaxMemberCount());
// 	}
//
// 	@Override
// 	public Long changeCafe(Long requestMemberId, Long studyOnceId, final Long changingCafeId) {
// 		final CafeStudy cafeStudy = findStudyOnceById(studyOnceId);
// 		if (!isStudyOnceLeader(requestMemberId, studyOnceId)) {
// 			throw new CafegoryException(STUDY_ONCE_LOCATION_CHANGE_PERMISSION_DENIED);
// 		}
// 		cafeStudy.changeCafe(findCafeById(changingCafeId));
// 		return changingCafeId;
// 	}
//
// 	@Override
// 	public StudyMemberListResponse findStudyMembersById(Long studyOnceId) {
// 		CafeStudy cafeStudy = findStudyOnceById(studyOnceId);
// 		List<MemberResponse> memberResponses = studyMemberMapper.toMemberResponses(cafeStudy.getStudyMembers());
// 		return new StudyMemberListResponse(memberResponses);
// 	}
//
// 	@Override
// 	public StudyOnceResponse findStudyOnce(Long studyOnceId, LocalDateTime now) {
// 		CafeStudy cafeStudy = findStudyOnceById(studyOnceId);
// 		return studyOnceMapper.toStudyOnceResponse(cafeStudy,
// 			cafeStudy.canJoin(truncateDateTimeToSecond(now)));
// 	}
//
// 	@Override
// 	public boolean doesOnlyStudyLeaderExist(Long studyOnceId) {
// 		CafeStudy cafeStudy = findStudyOnceById(studyOnceId);
// 		return cafeStudy.doesOnlyLeaderExist();
// 	}
//
// 	@Override
// 	public boolean isStudyOnceLeader(Long memberId, Long studyOnceId) {
// 		CafeStudy cafeStudy = findStudyOnceById(studyOnceId);
// 		return cafeStudy.isLeader(findMemberById(memberId));
// 	}
//
// 	private Member findMemberById(Long memberId) {
// 		return memberRepository.findById(memberId)
// 			.orElseThrow(() -> new CafegoryException(MEMBER_NOT_FOUND));
// 	}
//
// 	private Cafe findCafeById(Long cafeId) {
// 		return cafeRepository.findById(cafeId)
// 			.orElseThrow(() -> new CafegoryException(CAFE_NOT_FOUND));
// 	}
// }
