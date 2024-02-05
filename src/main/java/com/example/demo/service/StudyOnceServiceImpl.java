package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.CafeImpl;
import com.example.demo.domain.MemberImpl;
import com.example.demo.domain.StudyMember;
import com.example.demo.domain.StudyOnceImpl;
import com.example.demo.dto.PagedResponse;
import com.example.demo.dto.StudyOnceCreateRequest;
import com.example.demo.dto.StudyOnceSearchRequest;
import com.example.demo.dto.StudyOnceSearchResponse;
import com.example.demo.dto.UpdateAttendanceResponse;
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
		MemberImpl member = memberRepository.findById(memberIdThatExpectedToJoin)
			.orElseThrow(() -> new IllegalArgumentException("없는 회원입니다."));
		StudyOnceImpl studyOnce = studyOnceRepository.findById(studyId)
			.orElseThrow(() -> new IllegalArgumentException("없는 카공입니다."));
		LocalDateTime startDateTime = studyOnce.getStartDateTime();
		List<StudyMember> studyMembers = studyMemberRepository.findByMemberAndStudyDate(member,
			startDateTime.toLocalDate());
		member.setStudyMembers(studyMembers);
		studyOnce.tryJoin(member);
	}

	@Override
	public void tryQuit(long memberIdThatExpectedToQuit, long studyId) {
		MemberImpl member = memberRepository.findById(memberIdThatExpectedToQuit)
			.orElseThrow(() -> new IllegalArgumentException("없는 회원입니다."));
		StudyOnceImpl studyOnce = studyOnceRepository.findById(studyId)
			.orElseThrow(() -> new IllegalArgumentException("없는 카공입니다."));
		if (studyOnce.getLeader().equals(member)) {
			deleteStudy(studyOnce);
		}
		StudyMember needToRemoveStudyMember = studyOnce.tryQuit(member, LocalDateTime.now());
		studyMemberRepository.delete(needToRemoveStudyMember);
	}

	private void deleteStudy(StudyOnceImpl studyOnce) {
		if (studyOnce.getStudyMembers().size() > 1) {
			throw new IllegalStateException("카공장은 다른 참여자가 있는 경우 참여 취소를 할 수 없습니다.");
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
			.orElseThrow(() -> new IllegalArgumentException("해당 카공을 찾을 수 없습니다."));
		boolean canJoin = searched.canJoin(LocalDateTime.now());
		return makeStudyOnceSearchResponse(searched, canJoin);
	}

	@Override
	public List<UpdateAttendanceResponse> updateAttendance(long leaderId, long memberId, boolean attendance) {
		return null;
	}

	@Override
	public StudyOnceSearchResponse createStudy(long leaderId, StudyOnceCreateRequest studyOnceCreateRequest) {
		CafeImpl cafe = cafeRepository.findById(studyOnceCreateRequest.getCafeId()).orElseThrow();
		//ToDo 카페 영업시간 이내인지 확인 하는 작업 추가 필요
		MemberImpl leader = memberRepository.findById(leaderId).orElseThrow();
		validateAlreadyStudyLeader(leaderId, studyOnceCreateRequest);
		StudyOnceImpl studyOnce = makeNewStudyOnce(studyOnceCreateRequest, cafe, leader);
		StudyOnceImpl saved = studyOnceRepository.save(studyOnce);
		boolean canJoin = true;
		return makeStudyOnceSearchResponse(saved, canJoin);
	}

	private void validateAlreadyStudyLeader(long leaderId, StudyOnceCreateRequest studyOnceCreateRequest) {
		LocalDateTime startDateTime = studyOnceCreateRequest.getStartDateTime();
		LocalDateTime endDateTime = studyOnceCreateRequest.getEndDateTime();
		boolean existsByLeaderIdAndStudyTime = studyOnceRepository.existsByLeaderIdAndStudyTime(leaderId, startDateTime,
			endDateTime);
		if (existsByLeaderIdAndStudyTime) {
			throw new IllegalArgumentException("해당 시간에 이미 카공장으로 참여중인 스터디가 있습니다.");
		}
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
