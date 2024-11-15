package com.example.demo.study.implement;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.cafe.domain.CafeTags;
import com.example.demo.domain.Page;
import com.example.demo.exception.CafegoryException;
import com.example.demo.exception.ExceptionType;
import com.example.demo.study.domain.Participant;
import com.example.demo.study.domain.SearchCriteria;
import com.example.demo.study.domain.Study;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.study.infrastructure.CafeStudyQueryRepository;
import com.example.demo.study.infrastructure.CafeStudyRepository;
import com.example.demo.trash.dto.SliceResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyReader {

	private final CafeStudyRepository cafeStudyRepository;
	private final StudyMemberReader studyMemberReader;
	private final CafeStudyQueryRepository cafeStudyQueryRepository;

	public Study read(Long cafeStudyId) {
		CafeStudyEntity cafeStudyEntity = cafeStudyRepository.findById(cafeStudyId)
			.orElseThrow(() -> new CafegoryException(CAFE_STUDY_NOT_FOUND));

		return cafeStudyEntity.toStudy();
	}

	public List<Study> readUpcomingBy(Long memberId, LocalDateTime now) {
		List<Participant> upcomings = studyMemberReader.readMyUpcomingsBy(memberId);
		List<Long> studyIds = upcomings.stream().map(Participant::getStudyId).collect(Collectors.toList());

		return cafeStudyRepository.findUpcomingsBy(studyIds, now).stream()
			.map(CafeStudyEntity::toStudy)
			.collect(Collectors.toList());
	}

	public SliceResponse<CafeStudyEntity> searchCafeStudies(SearchCriteria searchCriteria, CafeTags cafeTags,
		Page page) {
		return cafeStudyQueryRepository.findCafeStudies(searchCriteria, cafeTags, page);
	}

	public List<CafeStudyEntity> readAllWithCoordinatorBy(Long cafeId) {
		return cafeStudyRepository.findAllByCafeId(cafeId);
	}

	public CafeStudyEntity readStudyEntity(Long cafeStudyId) {
		return cafeStudyRepository.findById(cafeStudyId)
			.orElseThrow(() -> new CafegoryException(ExceptionType.CAFE_STUDY_NOT_FOUND));
	}

}
