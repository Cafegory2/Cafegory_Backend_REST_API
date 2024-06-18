package com.example.demo.helper;

import java.time.LocalDateTime;

import com.example.demo.domain.cafe.Cafe;
import com.example.demo.domain.member.Member;
import com.example.demo.domain.study.StudyOnce;
import com.example.demo.factory.TestStudyOnceFactory;
import com.example.demo.repository.study.StudyOnceRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StudyOnceSaveHelper {

	private final StudyOnceRepository studyOnceRepository;

	public StudyOnce saveDefaultStudyOnce(Cafe cafe, Member leader) {
		StudyOnce studyOnce = TestStudyOnceFactory.createStudyOnce(cafe, leader);
		return studyOnceRepository.save(studyOnce);
	}

	public StudyOnce saveStudyOnceWithTime(Cafe cafe, Member leader, LocalDateTime startDateTime,
		LocalDateTime endDateTime) {
		StudyOnce studyOnce = TestStudyOnceFactory.createStudyOnceWithTime(cafe, leader, startDateTime, endDateTime);
		return studyOnceRepository.save(studyOnce);
	}
}
