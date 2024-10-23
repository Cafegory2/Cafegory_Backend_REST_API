package com.example.demo.helper;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import org.springframework.transaction.annotation.Transactional;

import com.example.demo.factory.TestCafeStudyFactory;
import com.example.demo.implement.cafe.CafeEntity;
import com.example.demo.implement.member.Member;
import com.example.demo.implement.study.CafeStudy;
import com.example.demo.implement.study.MemberComms;
import com.example.demo.implement.study.RecruitmentStatus;
import com.example.demo.repository.cafe.CafeRepository;
import com.example.demo.repository.member.MemberRepository;
import com.example.demo.repository.study.CafeStudyRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
public class CafeStudySaveHelper {

	private final CafeStudyRepository cafeStudyRepository;
	private final MemberRepository memberRepository;
	private final CafeRepository cafeRepository;

	public CafeStudy saveCafeStudy(CafeEntity cafeEntity, Member coordinator, LocalDateTime startDateTime,
                                   LocalDateTime endDateTime) {
		Member mergedLeader = memberRepository.save(coordinator);
		CafeEntity mergedCafeEntity = cafeRepository.save(cafeEntity);

		CafeStudy cafeStudy = TestCafeStudyFactory.createCafeStudy(mergedCafeEntity, mergedLeader, startDateTime,
			endDateTime);
		return cafeStudyRepository.save(cafeStudy);
	}

	public CafeStudy saveFinishedCafeStudy(CafeEntity cafeEntity, Member coordinator, LocalDateTime startDateTime,
                                           LocalDateTime endDateTime) throws Exception {
		Member mergedLeader = memberRepository.save(coordinator);
		CafeEntity mergedCafeEntity = cafeRepository.save(cafeEntity);

		CafeStudy cafeStudy = TestCafeStudyFactory.createCafeStudy(mergedCafeEntity, mergedLeader, startDateTime,
			endDateTime);

		Class<? extends CafeStudy> cafeStudyClass = cafeStudy.getClass();
		Field recruitmentStatusField = cafeStudyClass.getDeclaredField("recruitmentStatus");
		recruitmentStatusField.setAccessible(true);
		recruitmentStatusField.set(cafeStudy, RecruitmentStatus.CLOSED);

		return cafeStudyRepository.save(cafeStudy);
	}

	public CafeStudy saveCafeStudyWithName(CafeEntity cafeEntity, Member coordinator, LocalDateTime startDateTime,
                                           LocalDateTime endDateTime, String cafeStudyName) {
		Member mergedLeader = memberRepository.save(coordinator);
		CafeEntity mergedCafeEntity = cafeRepository.save(cafeEntity);

		CafeStudy cafeStudy = TestCafeStudyFactory.createCafeStudyWithName(mergedCafeEntity, mergedLeader, startDateTime,
			endDateTime, cafeStudyName);
		return cafeStudyRepository.save(cafeStudy);
	}

	public CafeStudy saveCafeStudyWithMemberComms(CafeEntity cafeEntity, Member coordinator, LocalDateTime startDateTime,
                                                  LocalDateTime endDateTime, MemberComms memberComms) {
		Member mergedLeader = memberRepository.save(coordinator);
		CafeEntity mergedCafeEntity = cafeRepository.save(cafeEntity);

		CafeStudy cafeStudy = TestCafeStudyFactory.createCafeStudyWithMemberComms(mergedCafeEntity, mergedLeader,
			startDateTime, endDateTime, memberComms);
		return cafeStudyRepository.save(cafeStudy);
	}

	//	public StudyOnce saveStudyOnceWithTime(Cafe cafe, Member leader, LocalDateTime startDateTime,
	//		LocalDateTime endDateTime) {
	//		Member mergedLeader = memberRepository.save(leader);
	//		Cafe mergedCafe = cafeRepository.save(cafe);
	//		StudyOnce studyOnce = TestStudyOnceFactory.createStudyOnceWithTime(mergedCafe, mergedLeader, startDateTime,
	//			endDateTime);
	//		return studyOnceRepository.save(studyOnce);
	//	}
}
