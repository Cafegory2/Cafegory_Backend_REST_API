package com.example.demo.domain.study.implement;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.demo.auth.dto.SliceResponse;
import com.example.demo.db.study.CafeStudySearchListRequest;
import com.example.demo.db.study.CafeStudySearchListResponse;
import com.example.demo.db.study.repository2.StudyQueryRepository;
import com.example.demo.exception.CafegoryException;
import com.example.demo.domain.member.domain.MemberId;
import com.example.demo.domain.study.domain.Participant;
import com.example.demo.domain.study.domain.Study;
import com.example.demo.domain.study.domain.StudyId;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StudyReader {

	private final StudyMemberReader studyMemberReader;

	private final StudyQueryRepository studyQueryRepository;

	public Study read(StudyId studyId) {
		return studyQueryRepository.findWithMember(studyId)
			.orElseThrow(() -> new CafegoryException(CAFE_STUDY_NOT_FOUND));
	}

	public List<Study> readUpcomingBy(MemberId memberId, LocalDateTime now) {
		List<Participant> upcomings = studyMemberReader.readMyUpcomingsBy(memberId);
		List<StudyId> studyIds = upcomings.stream()
			.map(participant -> new StudyId(participant.getStudyId().getId())).collect(Collectors.toList());

		return studyQueryRepository.findUpcomingsWithMemberBy(studyIds, now);
	}

	public SliceResponse<CafeStudySearchListResponse> searchCafeStudies(CafeStudySearchListRequest request) {
		return studyQueryRepository.findCafeStudies(request);
	}

	public int readViewCountBy(StudyId studyId) {
		return studyQueryRepository.findViewCountBy(studyId);
	}
}
