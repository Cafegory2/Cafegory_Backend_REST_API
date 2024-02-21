package com.example.demo.service;

import static com.example.demo.exception.ExceptionType.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Attendance;
import com.example.demo.domain.CafeImpl;
import com.example.demo.domain.MemberImpl;
import com.example.demo.domain.StudyMember;
import com.example.demo.domain.StudyMemberId;
import com.example.demo.domain.StudyOnceImpl;
import com.example.demo.dto.PagedResponse;
import com.example.demo.dto.StudyMemberStateResponse;
import com.example.demo.dto.StudyOnceCreateRequest;
import com.example.demo.dto.StudyOnceSearchRequest;
import com.example.demo.dto.StudyOnceSearchResponse;
import com.example.demo.dto.UpdateAttendanceResponse;
import com.example.demo.exception.CafegoryException;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.StudyMemberRepository;
import com.example.demo.repository.StudyOnceRepository;
import com.example.demo.repository.cafe.CafeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyOnceServiceImpl implements StudyOnceService {

	private final CafeRepository cafeRepository;
	private final StudyOnceRepository studyOnceRepository;
	private final MemberRepository memberRepository;
	private final StudyMemberRepository studyMemberRepository;

	@Override
	public void tryJoin(long memberIdThatExpectedToJoin, long studyId) {
		StudyOnceImpl studyOnce = studyOnceRepository.findById(studyId)
			.orElseThrow(() -> new CafegoryException(STUDY_ONCE_NOT_FOUND));
		LocalDateTime startDateTime = studyOnce.getStartDateTime();
		MemberImpl member = getMember(memberIdThatExpectedToJoin, startDateTime);
		studyOnce.tryJoin(member, LocalDateTime.now());
	}

	@Override
	public void tryQuit(long memberIdThatExpectedToQuit, long studyId) {
		MemberImpl member = memberRepository.findById(memberIdThatExpectedToQuit)
			.orElseThrow(() -> new CafegoryException(MEMBER_NOT_FOUND));
		StudyOnceImpl studyOnce = studyOnceRepository.findById(studyId)
			.orElseThrow(() -> new CafegoryException(STUDY_ONCE_NOT_FOUND));
		if (studyOnce.getLeader().equals(member)) {
			deleteStudy(studyOnce);
		}
		StudyMember needToRemoveStudyMember = studyOnce.tryQuit(member, LocalDateTime.now());
		studyMemberRepository.delete(needToRemoveStudyMember);
	}

	private void deleteStudy(StudyOnceImpl studyOnce) {
		if (studyOnce.getStudyMembers().size() > 1) {
			throw new CafegoryException(STUDY_ONCE_LEADER_QUIT_FAIL);
		}
		studyOnceRepository.delete(studyOnce);
	}

	@Override
	public PagedResponse<StudyOnceSearchResponse> searchStudy(StudyOnceSearchRequest studyOnceSearchRequest) {
		int totalCount = Math.toIntExact(studyOnceRepository.count(studyOnceSearchRequest));
		int sizePerPage = studyOnceSearchRequest.getSizePerPage();
		int maxPage = calculateMaxPage(totalCount, sizePerPage);
		List<StudyOnceSearchResponse> searchResults = studyOnceRepository.findAllByStudyOnceSearchRequest(
				studyOnceSearchRequest)
			.stream()
			.map(studyOnce -> makeStudyOnceSearchResponse(studyOnce, studyOnce.canJoin(LocalDateTime.now())))
			.collect(Collectors.toList());
		return new PagedResponse<>(studyOnceSearchRequest.getPage(), maxPage, searchResults.size(), searchResults);
	}

	private int calculateMaxPage(int totalCount, int sizePerPage) {
		if (totalCount % sizePerPage == 0) {
			return totalCount / sizePerPage;
		}
		return totalCount / sizePerPage + 1;
	}

	@Override
	public StudyOnceSearchResponse searchByStudyId(long studyId) {
		StudyOnceImpl searched = studyOnceRepository.findById(studyId)
			.orElseThrow(() -> new CafegoryException(STUDY_ONCE_NOT_FOUND));
		boolean canJoin = searched.canJoin(LocalDateTime.now());
		return makeStudyOnceSearchResponse(searched, canJoin);
	}

	@Override
	public UpdateAttendanceResponse updateAttendances(long leaderId, long studyOnceId,
		Map<Long, Attendance> memberAttendances, LocalDateTime now) {
		for (Map.Entry<Long, Attendance> entry : memberAttendances.entrySet()) {
			updateAttendance(leaderId, studyOnceId, entry.getKey(), entry.getValue(), now);
		}
		List<StudyMemberId> studyMemberIds = memberAttendances.keySet().stream()
			.map(memberId -> new StudyMemberId(memberId, studyOnceId))
			.collect(Collectors.toList());
		return new UpdateAttendanceResponse(studyMemberRepository.findAllById(studyMemberIds).stream()
			.map(studyMember -> new StudyMemberStateResponse(studyMember.getId().getMemberId(),
				studyMember.getAttendance().isPresent(), LocalDateTime.of(2999, 12, 31, 12, 0)))
			.collect(Collectors.toList()));
	}

	@Override
	public void updateAttendance(long leaderId, long studyOnceId, long memberId, Attendance attendance,
		LocalDateTime now) {
		if (!studyOnceRepository.existsByLeaderId(leaderId)) {
			throw new CafegoryException(STUDY_ONCE_INVALID_LEADER);
		}

		StudyOnceImpl searched = findStudyOnceById(studyOnceId);
		validateEarlyToTakeAttendance(now, searched.getStartDateTime());
		validateLateToTakeAttendance(now, searched.getStartDateTime(), searched.getEndDateTime());

		StudyMember findStudyMember = studyMemberRepository.findById(new StudyMemberId(memberId, studyOnceId))
			.orElseThrow(() -> new CafegoryException(STUDY_MEMBER_NOT_FOUND));
		findStudyMember.setAttendance(attendance);
	}

	private void validateLateToTakeAttendance(LocalDateTime now, LocalDateTime startDateTime,
		LocalDateTime endDateTime) {
		Duration halfDuration = Duration.between(startDateTime, endDateTime).dividedBy(2);
		LocalDateTime midTime = startDateTime.plus(halfDuration);

		if (now.isAfter(midTime)) {
			throw new CafegoryException(STUDY_ONCE_LATE_TAKE_ATTENDANCE);
		}
	}

	private void validateEarlyToTakeAttendance(LocalDateTime now, LocalDateTime startDateTime) {
		if (now.minusMinutes(10).isBefore(startDateTime)) {
			throw new CafegoryException(STUDY_ONCE_EARLY_TAKE_ATTENDANCE);
		}
	}

	private StudyOnceImpl findStudyOnceById(long studyOnceId) {
		return studyOnceRepository.findById(studyOnceId)
			.orElseThrow(() -> new CafegoryException(STUDY_ONCE_NOT_FOUND));
	}

	@Override
	public StudyOnceSearchResponse createStudy(long leaderId, StudyOnceCreateRequest studyOnceCreateRequest) {
		CafeImpl cafe = cafeRepository.findById(studyOnceCreateRequest.getCafeId())
			.orElseThrow(() -> new CafegoryException(CAFE_NOT_FOUND));
		//ToDo 카페 영업시간 이내인지 확인 하는 작업 추가 필요
		LocalDateTime startDateTime = studyOnceCreateRequest.getStartDateTime();
		MemberImpl leader = getMember(leaderId, startDateTime);
		StudyOnceImpl studyOnce = makeNewStudyOnce(studyOnceCreateRequest, cafe, leader);
		StudyOnceImpl saved = studyOnceRepository.save(studyOnce);
		boolean canJoin = true;
		return makeStudyOnceSearchResponse(saved, canJoin);
	}

	private MemberImpl getMember(long leaderId, LocalDateTime startDateTime) {
		MemberImpl leader = memberRepository.findById(leaderId)
			.orElseThrow(() -> new CafegoryException(MEMBER_NOT_FOUND));
		var studyMembers = studyMemberRepository.findByMemberAndStudyDate(leader, startDateTime.toLocalDate());
		leader.setStudyMembers(studyMembers);
		return leader;
	}

	private static StudyOnceImpl makeNewStudyOnce(StudyOnceCreateRequest studyOnceCreateRequest, CafeImpl cafe,
		MemberImpl leader) {
		return StudyOnceImpl.builder()
			.name(studyOnceCreateRequest.getName())
			.startDateTime(studyOnceCreateRequest.getStartDateTime())
			.endDateTime(studyOnceCreateRequest.getEndDateTime())
			.maxMemberCount(studyOnceCreateRequest.getMaxMemberCount())
			.nowMemberCount(0)
			.isEnd(false)
			.ableToTalk(studyOnceCreateRequest.isCanTalk())
			.cafe(cafe)
			.leader(leader)
			.build();

	}

	private static StudyOnceSearchResponse makeStudyOnceSearchResponse(StudyOnceImpl saved, boolean canJoin) {
		return StudyOnceSearchResponse.builder()
			.cafeId(saved.getCafe().getId())
			.id(saved.getId())
			.name(saved.getName())
			.startDateTime(saved.getStartDateTime())
			.endDateTime(saved.getEndDateTime())
			.maxMemberCount(saved.getMaxMemberCount())
			.nowMemberCount(saved.getNowMemberCount())
			.canTalk(saved.isAbleToTalk())
			.canJoin(canJoin)
			.isEnd(saved.isEnd())
			.build();
	}
}
