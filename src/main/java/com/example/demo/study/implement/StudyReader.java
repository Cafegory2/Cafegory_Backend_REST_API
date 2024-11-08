package com.example.demo.study.implement;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.demo.exception.CafegoryException;
import com.example.demo.study.domain.Participant;
import com.example.demo.study.domain.Study;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.study.infrastructure.CafeStudyRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StudyReader {

	private final CafeStudyRepository cafeStudyRepository;
	private final StudyMemberReader studyMemberReader;

	public Study read(Long cafeStudyId) {
		CafeStudyEntity cafeStudyEntity = cafeStudyRepository.findById(cafeStudyId)
			.orElseThrow(() -> new CafegoryException(CAFE_STUDY_NOT_FOUND));

		return cafeStudyEntity.toStudy();
	}

	public List<Study> readUpcomingBy(Long memberId, LocalDateTime now) {
		List<Participant> upcomings = studyMemberReader.read(memberId);
		List<Long> studyIds = upcomings.stream().map(Participant::getStudyId).collect(Collectors.toList());

		return cafeStudyRepository.findUpcomingsBy(studyIds, now).stream()
			.map(CafeStudyEntity::toStudy)
			.collect(Collectors.toList());
	}

}
