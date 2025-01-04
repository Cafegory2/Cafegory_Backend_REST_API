package com.example.demo.study.infrastructure.repository2;

import java.time.LocalDateTime;

import com.example.demo.cafe.domain.CafeId;
import com.example.demo.member.domain.MemberId;
import com.example.demo.study.domain.Study;
import com.example.demo.study.domain.StudyContent;
import com.example.demo.study.domain.StudyId;

public interface StudyRepository2 {

	StudyId save(StudyContent content, CafeId cafeId, MemberId memberId);

	void deleteWithCascade(StudyId studyId, MemberId memberId, LocalDateTime now);
}
