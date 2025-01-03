package com.example.demo.study.implement;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.study.infrastructure.CafeStudySearchListResponse;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.CafegoryException;
import com.example.demo.member.domain.MemberId;
import com.example.demo.study.domain.Participant;
import com.example.demo.study.domain.Study;
import com.example.demo.study.domain.StudyId;
import com.example.demo.study.domain.ViewCount;
import com.example.demo.study.infrastructure.CafeStudySearchListRequest;
import com.example.demo.study.infrastructure.repository2.StudyQueryRepository2;
import com.example.demo.auth.dto.SliceResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyReader {

	private final StudyMemberReader studyMemberReader;

	private final StudyQueryRepository2 studyQueryRepository2;

	public Study read(StudyId studyId) {
		return studyQueryRepository2.findWithMember(studyId)
			.orElseThrow(() -> new CafegoryException(CAFE_STUDY_NOT_FOUND));
	}

	public List<Study> readUpcomingBy(MemberId memberId, LocalDateTime now) {
		List<Participant> upcomings = studyMemberReader.readMyUpcomingsBy(memberId);
		List<StudyId> studyIds = upcomings.stream()
			.map(participant -> new StudyId(participant.getStudyId().getId())).collect(Collectors.toList());

		return studyQueryRepository2.findUpcomingsWithMemberBy(studyIds, now);
	}

	public SliceResponse<CafeStudySearchListResponse> searchCafeStudies(CafeStudySearchListRequest request) {
		return studyQueryRepository2.findCafeStudies(request);
	}

	public ViewCount readViewCountBy(StudyId studyId) {
		return studyQueryRepository2.findViewCountBy(studyId)
			.orElseThrow(() -> new CafegoryException(CAFE_STUDY_NOT_FOUND));
	}
}
