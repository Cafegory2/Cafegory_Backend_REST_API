package com.example.demo.helper;

import java.time.LocalDateTime;

import com.example.demo.factory.TestCafeStudyFactory;
import com.example.demo.implement.study.CafeStudy;
import com.example.demo.repository.study.CafeStudyRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.implement.cafe.Cafe;
import com.example.demo.implement.member.Member;
import com.example.demo.repository.cafe.CafeRepository;
import com.example.demo.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
public class CafeStudySaveHelper {

	private final CafeStudyRepository cafeStudyRepository;
	private final MemberRepository memberRepository;
	private final CafeRepository cafeRepository;

//	public StudyOnce saveStudyOnce(Cafe cafe, Member leader) {
//		Member mergedMember = memberRepository.save(leader);
//		Cafe mergedCafe = cafeRepository.save(cafe);
//		StudyOnce studyOnce = TestStudyOnceFactory.createStudyOnce(mergedCafe, mergedMember);
//		return cafeStudyRepository.save(studyOnce);
//	}

	public CafeStudy saveCafeStudyWithName(Cafe cafe, Member leader, LocalDateTime startDateTime,
										   LocalDateTime endDateTime, String cafeStudyName) {
		Member mergedLeader = memberRepository.save(leader);
		Cafe mergedCafe = cafeRepository.save(cafe);

		CafeStudy cafeStudy = TestCafeStudyFactory.createCafeStudyWithName(mergedCafe, mergedLeader, startDateTime,
			endDateTime, cafeStudyName);
		return cafeStudyRepository.save(cafeStudy);
	}
}
