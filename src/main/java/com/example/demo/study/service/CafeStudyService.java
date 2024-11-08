package com.example.demo.study.service;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.demo.cafe.domain.BusinessHour;
import com.example.demo.cafe.domain.Cafe;
import com.example.demo.cafe.implement.CafeReader;
import com.example.demo.exception.CafegoryException;
import com.example.demo.implement.cafe.BusinessHourReader;
import com.example.demo.member.domain.Member;
import com.example.demo.member.implement.MemberReader;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.study.domain.Participant;
import com.example.demo.study.domain.Study;
import com.example.demo.study.domain.StudyRole;
import com.example.demo.study.implement.CafeStudyReader;
import com.example.demo.study.implement.StudyEditor;
import com.example.demo.study.implement.StudyMemberEditor;
import com.example.demo.study.implement.StudyMemberReader;
import com.example.demo.study.implement.StudyReader;
import com.example.demo.study.implement.StudyValidator;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.study.infrastructure.CafeStudyRepository;
import com.example.demo.study.infrastructure.StudyMemberRepository;
import com.example.demo.util.TimeUtil;
import com.example.demo.validator.BusinessHourValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CafeStudyService {
	private final CafeStudyRepository cafeStudyRepository;
	private final StudyMemberRepository studyMemberRepository;
	private final TimeUtil timeUtil;
	private final StudyValidator studyValidator;
	private final BusinessHourValidator businessHourValidator;
	private final CafeReader cafeReader;
	private final BusinessHourReader businessHourReader;
	private final CafeStudyReader cafeStudyReader;
	private final StudyEditor studyEditor;
	private final MemberReader memberReader;
	private final StudyReader studyReader;
	private final StudyMemberEditor studyMemberEditor;
	private final StudyMemberReader studyMemberReader;

	// @Override
	// public void tryJoin(long memberId, long studyId) {
	// 	CafeStudy cafeStudy = studyOnceRepository.findById(studyId)
	// 		.orElseThrow(() -> new CafegoryException(STUDY_ONCE_NOT_FOUND));
	// 	Member member = findMemberById(memberId);
	// 	cafeStudy.tryJoin(member, LocalDateTime.now());
	// }
	//
	// @Override
	// public void tryQuit(long memberIdThatExpectedToQuit, long studyId) {
	// 	Member member = memberRepository.findById(memberIdThatExpectedToQuit)
	// 		.orElseThrow(() -> new CafegoryException(MEMBER_NOT_FOUND));
	// 	CafeStudy cafeStudy = studyOnceRepository.findById(studyId)
	// 		.orElseThrow(() -> new CafegoryException(STUDY_ONCE_NOT_FOUND));
	// 	if (cafeStudy.getLeader().equals(member)) {
	// 		deleteStudy(cafeStudy);
	// 	}
	// 	CafeStudyMember needToRemoveCafeStudyMember = cafeStudy.tryQuit(member, LocalDateTime.now());
	// 	studyMemberRepository.delete(needToRemoveCafeStudyMember);
	// }
	//
	//
	// @Override
	// public StudyOnceSearchResponse searchByStudyId(long studyId) {
	// 	CafeStudy searched = studyOnceRepository.findById(studyId)
	// 		.orElseThrow(() -> new CafegoryException(STUDY_ONCE_NOT_FOUND));
	// 	boolean canJoin = searched.canJoin(LocalDateTime.now());
	// 	return studyOnceMapper.toStudyOnceSearchResponse(searched, canJoin, false);
	// }
	//
	// @Override
	// public StudyOnceSearchResponse searchStudyOnceWithMemberParticipation(long studyOnceId, long memberId) {
	// 	CafeStudy searched = studyOnceRepository.findById(studyOnceId)
	// 		.orElseThrow(() -> new CafegoryException(STUDY_ONCE_NOT_FOUND));
	// 	boolean canJoin = searched.canJoin(LocalDateTime.now());
	// 	boolean isAttendance = searched.isAttendance(findMemberById(memberId));
	// 	return studyOnceMapper.toStudyOnceSearchResponse(searched, canJoin, isAttendance);
	// }
	//
	// @Override
	// public UpdateAttendanceResponse updateAttendances(long leaderId, long studyOnceId,
	// 	UpdateAttendanceRequest request, LocalDateTime now) {
	// 	processAttendanceUpdates(leaderId, studyOnceId, request, now);
	//
	// 	List<StudyMemberId> studyMemberIds = generateStudyMemberIdsFromRequest(studyOnceId, request);
	// 	List<CafeStudyMember> cafeStudyMembers = studyMemberRepository.findAllById(studyMemberIds);
	// 	return new UpdateAttendanceResponse(mapToStateResponses(cafeStudyMembers));
	// }
	//
	// private List<StudyMemberStateResponse> mapToStateResponses(List<CafeStudyMember> cafeStudyMembers) {
	// 	return cafeStudyMembers.stream()
	// 		.map(studyMember -> new StudyMemberStateResponse(studyMember.getId().getMemberId(),
	// 			studyMember.getAttendance().isPresent(), studyMember.getLastModifiedDate()))
	// 		.collect(Collectors.toList());
	// }
	//
	// private List<StudyMemberId> generateStudyMemberIdsFromRequest(long studyOnceId, UpdateAttendanceRequest request) {
	// 	return request.getStates().stream()
	// 		.map(memberReq -> new StudyMemberId(memberReq.getMemberId(), studyOnceId))
	// 		.collect(Collectors.toList());
	// }
	//
	// private void processAttendanceUpdates(long leaderId, long studyOnceId, UpdateAttendanceRequest request,
	// 	LocalDateTime now) {
	// 	for (StudyMemberStateRequest memberStateRequest : request.getStates()) {
	// 		Attendance attendance = memberStateRequest.isAttendance() ? Attendance.YES : Attendance.NO;
	// 		updateAttendance(leaderId, studyOnceId, memberStateRequest.getMemberId(), attendance, now);
	// 	}
	// }
	//
	// @Override
	// public void updateAttendance(long leaderId, long studyOnceId, long memberId, Attendance attendance,
	// 	LocalDateTime now) {
	// 	LocalDateTime microNow = truncateDateTimeToSecond(now);
	// 	CafeStudy searched = findStudyOnceById(studyOnceId);
	//
	// 	if (!studyOnceRepository.existsByLeaderId(leaderId)) {
	// 		throw new CafegoryException(STUDY_ONCE_INVALID_LEADER);
	// 	}
	// 	validateEarlyToTakeAttendance(microNow, searched.getStartDateTime());
	// 	validateLateToTakeAttendance(microNow, searched.getStartDateTime(), searched.getEndDateTime());
	//
	// 	CafeStudyMember findCafeStudyMember = studyMemberRepository.findById(new StudyMemberId(memberId, studyOnceId))
	// 		.orElseThrow(() -> new CafegoryException(STUDY_MEMBER_NOT_FOUND));
	// 	findCafeStudyMember.setAttendance(attendance);
	// 	findCafeStudyMember.setLastModifiedDate(microNow);
	// }
	//
	// private void validateLateToTakeAttendance(LocalDateTime now, LocalDateTime startDateTime,
	// 	LocalDateTime endDateTime) {
	// 	Duration halfDuration = Duration.between(startDateTime, endDateTime).dividedBy(2);
	// 	LocalDateTime midTime = startDateTime.plus(halfDuration);
	//
	// 	if (now.isAfter(midTime)) {
	// 		throw new CafegoryException(STUDY_ONCE_LATE_TAKE_ATTENDANCE);
	// 	}
	// }
	//
	// private void validateEarlyToTakeAttendance(LocalDateTime now, LocalDateTime startDateTime) {
	// 	if (now.minusMinutes(10).isBefore(startDateTime)) {
	// 		throw new CafegoryException(STUDY_ONCE_EARLY_TAKE_ATTENDANCE);
	// 	}
	// }

	public CafeStudyEntity findCafeStudyById(Long cafeStudyId) {
		return cafeStudyRepository.findById(cafeStudyId).orElseThrow(() -> new CafegoryException(CAFE_STUDY_NOT_FOUND));
	}

	@Transactional
	public Study createStudy(Long memberId, LocalDateTime now, Study study) {
		validateStudyCreation(study.getName(), now, study.getSchedule().getStartDateTime());

		Cafe cafe = cafeReader.read(study.getCafeId());
		BusinessHour businessHour = businessHourReader.readBy(cafe.getId(), study.getStartDate());
		businessHourValidator.validateBetweenBusinessHour(study.getSchedule(), businessHour);

		validateStudyScheduleConflict(
			buildLocalDateTime(study.getStartDateTime()),
			buildLocalDateTime(study.getEndDateTime()),
			memberId);

		Long savedStudyId = studyEditor.save(study, cafe, memberId);
		studyMemberEditor.save(memberId, savedStudyId, StudyRole.COORDINATOR);

		return studyReader.read(savedStudyId);
	}

	// TODO: 카공장 삭제하기 해야됨!!!!!!
	@Transactional
	public Long deleteStudy(Long memberId, Long cafeStudyId, LocalDateTime now) {
		CafeStudyEntity cafeStudy = cafeStudyReader.read(cafeStudyId);
		MemberEntity member = memberReader.readMemberEntity(memberId);
		validateStudyDelete(member, cafeStudy);

		cafeStudy.softDelete(now);
		return cafeStudy.getId();
	}

	private LocalDateTime buildLocalDateTime(LocalDateTime localDateTime) {
		return timeUtil.localDateTime(
			localDateTime.getYear(),
			localDateTime.getMonthValue(),
			localDateTime.getDayOfMonth(),
			localDateTime.getHour(),
			localDateTime.getMinute(),
			localDateTime.getSecond()
		);
	}

	private void validateStudyCreation(String name, LocalDateTime now, LocalDateTime startDateTime) {
		studyValidator.validateStartDateTime(now, startDateTime);
		studyValidator.validateStartDate(startDateTime);
	}

	private void validateStudyScheduleConflict(LocalDateTime start, LocalDateTime end, Long memberId) {
		if (hasStudyScheduleConflict(start, end, memberId)) {
			throw new CafegoryException(STUDY_ONCE_CONFLICT_TIME);
		}
	}

	private boolean hasStudyScheduleConflict(LocalDateTime start, LocalDateTime end, Long memberId) {
		List<Participant> participants = studyMemberReader.read(memberId);

		// 멤버가 참여중인 스터디가 생성하는 스터디의 시간과 겹치는게 있는지 검증...
		// StudyMember - Study - StudyPeriod
		// Participant - Study - Schedule
		return participants.stream()
			.anyMatch(participant -> isConflictWith(start, end));
	}

	private void validateStudyDelete(MemberEntity member, CafeStudyEntity cafeStudy) {
		studyValidator.validateMemberIsCafeStudyCoordinator(member.getId(), cafeStudy);
		studyValidator.validateCafeStudyMembersPresent(member, cafeStudy);
	}

	public boolean isConflictWith(LocalDateTime start, LocalDateTime end) {
		LocalDateTime studyStartDateTime = cafeStudy.getStudyPeriod().getStartDateTime();
		LocalDateTime studyEndDateTime = cafeStudy.getStudyPeriod().getEndDateTime();
		return (start.isBefore(studyEndDateTime) || start.isEqual(studyEndDateTime)) && (
			studyStartDateTime.isBefore(end) || studyStartDateTime.isEqual(end));
	}

	// @Override
	// public void updateStudyOnce(long requestedMemberId, long studyOnceId, StudyOnceUpdateRequest request,
	// 	LocalDateTime now) {
	// 	CafeStudy cafeStudy = findStudyOnceById(studyOnceId);
	// 	if (!isStudyOnceLeader(requestedMemberId, studyOnceId)) {
	// 		throw new CafegoryException(STUDY_ONCE_LEADER_PERMISSION_DENIED);
	// 	}
	// 	if (request.getCafeId() != null) {
	// 		cafeStudy.changeCafe(findCafeById(request.getCafeId()));
	// 	}
	// 	if (request.getName() != null) {
	// 		cafeStudy.changeName(request.getName());
	// 	}
	// 	if (request.getStartDateTime() != null && request.getEndDateTime() != null) {
	// 		Cafe cafe = cafeStudy.getCafe();
	// 		validateBetweenBusinessHour(request.getStartDateTime().toLocalTime(),
	// 			request.getEndDateTime().toLocalTime(),
	// 			cafe.findBusinessHour(truncateDateTimeToSecond(now).getDayOfWeek()));
	// 		cafeStudy.changeStudyOnceTime(request.getStartDateTime(), request.getEndDateTime());
	// 	}
	// 	if (request.getOpenChatUrl() != null) {
	// 		cafeStudy.changeOpenChatUrl(request.getOpenChatUrl());
	// 	}
	// 	cafeStudy.changeMaxMemberCount(request.getMaxMemberCount());
	// 	cafeStudy.changeCanTalk(request.isCanTalk());
	// }
	//
	// @Override
	// public void updateStudyOncePartially(long requestedMemberId, long studyOnceId, StudyOnceUpdateRequest request) {
	// 	CafeStudy cafeStudy = findStudyOnceById(studyOnceId);
	// 	if (!isStudyOnceLeader(requestedMemberId, studyOnceId)) {
	// 		throw new CafegoryException(STUDY_ONCE_LEADER_PERMISSION_DENIED);
	// 	}
	// 	if (request.getCafeId() != null) {
	// 		cafeStudy.changeCafe(findCafeById(request.getCafeId()));
	// 	}
	// 	cafeStudy.changeMaxMemberCount(request.getMaxMemberCount());
	// }
	//
	// @Override
	// public Long changeCafe(Long requestMemberId, Long studyOnceId, final Long changingCafeId) {
	// 	final CafeStudy cafeStudy = findStudyOnceById(studyOnceId);
	// 	if (!isStudyOnceLeader(requestMemberId, studyOnceId)) {
	// 		throw new CafegoryException(STUDY_ONCE_LOCATION_CHANGE_PERMISSION_DENIED);
	// 	}
	// 	cafeStudy.changeCafe(findCafeById(changingCafeId));
	// 	return changingCafeId;
	// }
	//
	// @Override
	// public StudyMemberListResponse findStudyMembersById(Long studyOnceId) {
	// 	CafeStudy cafeStudy = findStudyOnceById(studyOnceId);
	// 	List<MemberResponse> memberResponses = studyMemberMapper.toMemberResponses(cafeStudy.getStudyMembers());
	// 	return new StudyMemberListResponse(memberResponses);
	// }
	//
	// @Override
	// public StudyOnceResponse findStudyOnce(Long studyOnceId, LocalDateTime now) {
	// 	CafeStudy cafeStudy = findStudyOnceById(studyOnceId);
	// 	return studyOnceMapper.toStudyOnceResponse(cafeStudy,
	// 		cafeStudy.canJoin(truncateDateTimeToSecond(now)));
	// }
	//
	// @Override
	// public boolean doesOnlyStudyLeaderExist(Long studyOnceId) {
	// 	CafeStudy cafeStudy = findStudyOnceById(studyOnceId);
	// 	return cafeStudy.doesOnlyLeaderExist();
	// }
	//
	// @Override
	// public boolean isStudyOnceLeader(Long memberId, Long studyOnceId) {
	// 	CafeStudy cafeStudy = findStudyOnceById(studyOnceId);
	// 	return cafeStudy.isLeader(findMemberById(memberId));
	// }
	// @Override
	// public void updateStudyOnce(long requestedMemberId, long studyOnceId, StudyOnceUpdateRequest request,
	// 	LocalDateTime now) {
	// 	CafeStudy cafeStudy = findStudyOnceById(studyOnceId);
	// 	if (!isStudyOnceLeader(requestedMemberId, studyOnceId)) {
	// 		throw new CafegoryException(STUDY_ONCE_LEADER_PERMISSION_DENIED);
	// 	}
	// 	if (request.getCafeId() != null) {
	// 		cafeStudy.changeCafe(findCafeById(request.getCafeId()));
	// 	}
	// 	if (request.getName() != null) {
	// 		cafeStudy.changeName(request.getName());
	// 	}
	// 	if (request.getStartDateTime() != null && request.getEndDateTime() != null) {
	// 		Cafe cafe = cafeStudy.getCafe();
	// 		validateBetweenBusinessHour(request.getStartDateTime().toLocalTime(),
	// 			request.getEndDateTime().toLocalTime(),
	// 			cafe.findBusinessHour(truncateDateTimeToSecond(now).getDayOfWeek()));
	// 		cafeStudy.changeStudyOnceTime(request.getStartDateTime(), request.getEndDateTime());
	// 	}
	// 	if (request.getOpenChatUrl() != null) {
	// 		cafeStudy.changeOpenChatUrl(request.getOpenChatUrl());
	// 	}
	// 	cafeStudy.changeMaxMemberCount(request.getMaxMemberCount());
	// 	cafeStudy.changeCanTalk(request.isCanTalk());
	// }
	//
	// @Override
	// public void updateStudyOncePartially(long requestedMemberId, long studyOnceId, StudyOnceUpdateRequest request) {
	// 	CafeStudy cafeStudy = findStudyOnceById(studyOnceId);
	// 	if (!isStudyOnceLeader(requestedMemberId, studyOnceId)) {
	// 		throw new CafegoryException(STUDY_ONCE_LEADER_PERMISSION_DENIED);
	// 	}
	// 	if (request.getCafeId() != null) {
	// 		cafeStudy.changeCafe(findCafeById(request.getCafeId()));
	// 	}
	// 	cafeStudy.changeMaxMemberCount(request.getMaxMemberCount());
	// }
	//
	// @Override
	// public Long changeCafe(Long requestMemberId, Long studyOnceId, final Long changingCafeId) {
	// 	final CafeStudy cafeStudy = findStudyOnceById(studyOnceId);
	// 	if (!isStudyOnceLeader(requestMemberId, studyOnceId)) {
	// 		throw new CafegoryException(STUDY_ONCE_LOCATION_CHANGE_PERMISSION_DENIED);
	// 	}
	// 	cafeStudy.changeCafe(findCafeById(changingCafeId));
	// 	return changingCafeId;
	// }
	//
	// @Override
	// public StudyMemberListResponse findStudyMembersById(Long studyOnceId) {
	// 	CafeStudy cafeStudy = findStudyOnceById(studyOnceId);
	// 	List<MemberResponse> memberResponses = studyMemberMapper.toMemberResponses(cafeStudy.getStudyMembers());
	// 	return new StudyMemberListResponse(memberResponses);
	// }
	//
	// @Override
	// public StudyOnceResponse findStudyOnce(Long studyOnceId, LocalDateTime now) {
	// 	CafeStudy cafeStudy = findStudyOnceById(studyOnceId);
	// 	return studyOnceMapper.toStudyOnceResponse(cafeStudy,
	// 		cafeStudy.canJoin(truncateDateTimeToSecond(now)));
	// }
	//
	// @Override
	// public boolean doesOnlyStudyLeaderExist(Long studyOnceId) {
	// 	CafeStudy cafeStudy = findStudyOnceById(studyOnceId);
	// 	return cafeStudy.doesOnlyLeaderExist();
	// }
	//
	// @Override
	// public boolean isStudyOnceLeader(Long memberId, Long studyOnceId) {
	// 	CafeStudy cafeStudy = findStudyOnceById(studyOnceId);
	// 	return cafeStudy.isLeader(findMemberById(memberId));
	// }

	private Member findMemberById(Long memberId) {
		return memberReader.read(memberId);
	}
}
