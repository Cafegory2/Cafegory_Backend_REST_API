package com.example.demo.domain.study.repository;

import java.time.LocalDateTime;

import com.example.demo.domain.cafe.domain.CafeId;
import com.example.demo.domain.member.domain.MemberId;
import com.example.demo.domain.study.domain.StudyContent;
import com.example.demo.domain.study.domain.StudyId;

public interface StudyRepository {

	StudyId save(StudyContent content, CafeId cafeId, MemberId memberId);

	void deleteWithCascade(StudyId studyId, MemberId memberId, LocalDateTime now);
}
