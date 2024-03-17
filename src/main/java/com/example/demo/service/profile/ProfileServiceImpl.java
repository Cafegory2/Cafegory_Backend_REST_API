package com.example.demo.service.profile;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.MemberImpl;
import com.example.demo.domain.StudyMember;
import com.example.demo.domain.StudyOnceImpl;
import com.example.demo.dto.profile.ProfileResponse;
import com.example.demo.exception.CafegoryException;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.StudyMemberRepository;
import com.example.demo.repository.StudyOnceRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
	private final MemberRepository memberRepository;
	private final StudyOnceRepository studyOnceRepository;
	private final StudyMemberRepository studyMemberRepository;

	@Override
	public ProfileResponse get(Long requestMemberID, Long targetMemberID, LocalDateTime baseDateTime) {
		if (Objects.equals(requestMemberID, targetMemberID)) {
			MemberImpl member = memberRepository.findById(targetMemberID).orElseThrow();
			return new ProfileResponse(member.getName(), member.getThumbnailImage().getThumbnailImage(), "");
		}
		List<StudyOnceImpl> studyOnceByLeaderID = findStudyOnceByLeaderID(requestMemberID);
		Set<Long> memberIdInStudyOnce = getMemberIdInStudyOnce(studyOnceByLeaderID);
		if (memberIdInStudyOnce.contains(targetMemberID)) {
			MemberImpl member = memberRepository.findById(targetMemberID).orElseThrow();
			return new ProfileResponse(member.getName(), member.getThumbnailImage().getThumbnailImage(), "");
		}
		MemberImpl requestMember = memberRepository.findById(requestMemberID).orElseThrow();
		List<StudyMember> studyMembers = studyMemberRepository.findByMemberAndStudyDate(requestMember,
				LocalDate.from(baseDateTime))
			.stream()
			.map(StudyMember::getStudy)
			.flatMap(studyOnce -> studyOnce.getStudyMembers().stream())
			.collect(Collectors.toList());
		Set<Long> memberIdsThatJoinWithRequestMember = studyMembers.stream()
			.map(StudyMember::getStudy)
			.filter(studyOnce -> !studyOnce.canJoin(baseDateTime))
			.filter(studyOnce -> !studyOnce.isEnd())
			.flatMap(studyOnce -> studyOnce.getStudyMembers().stream())
			.map(StudyMember::getMember)
			.map(MemberImpl::getId)
			.collect(Collectors.toSet());
		if (memberIdsThatJoinWithRequestMember.contains(targetMemberID)) {
			MemberImpl member = memberRepository.findById(targetMemberID).orElseThrow();
			return new ProfileResponse(member.getName(), member.getThumbnailImage().getThumbnailImage(), "");
		}
		throw new CafegoryException(PROFILE_GET_PERMISSION_DENIED);
	}

	private List<StudyOnceImpl> findStudyOnceByLeaderID(Long requestMemberID) {
		return studyOnceRepository.findByLeaderId(requestMemberID);
	}

	private static Set<Long> getMemberIdInStudyOnce(List<StudyOnceImpl> byLeaderId) {
		return byLeaderId.stream()
			.flatMap(
				studyOnce -> studyOnce.getStudyMembers().stream().map(studyMember -> studyMember.getMember().getId()))
			.collect(Collectors.toSet());
	}
}
