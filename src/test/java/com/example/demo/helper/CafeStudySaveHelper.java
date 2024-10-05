package com.example.demo.helper;

<<<<<<< HEAD
import java.lang.reflect.Field;
import java.time.LocalDateTime;

import org.springframework.transaction.annotation.Transactional;

import com.example.demo.factory.TestCafeStudyFactory;
import com.example.demo.implement.cafe.Cafe;
import com.example.demo.implement.member.Member;
import com.example.demo.implement.study.CafeStudy;
import com.example.demo.implement.study.MemberComms;
import com.example.demo.implement.study.RecruitmentStatus;
import com.example.demo.repository.cafe.CafeRepository;
import com.example.demo.repository.member.MemberRepository;
<<<<<<< HEAD
import com.example.demo.repository.study.CafeStudyRepository;
=======
=======
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.factory.TestCafeStudyFactory;
import com.example.demo.implement.cafe.Cafe;
import com.example.demo.implement.member.Member;
import com.example.demo.implement.study.CafeStudy;
import com.example.demo.repository.study.CafeStudyRepository;
>>>>>>> ba34f9b (test: 스터디 삭제 테스트 작성)
>>>>>>> d0e38a8 (feat: 스터디 삭제하는 기능 구현)

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
public class CafeStudySaveHelper {

	private final CafeStudyRepository cafeStudyRepository;
<<<<<<< HEAD
	private final MemberRepository memberRepository;
	private final CafeRepository cafeRepository;

	public CafeStudy saveCafeStudy(Cafe cafe, Member coordinator, LocalDateTime startDateTime,
		LocalDateTime endDateTime) {
		Member mergedLeader = memberRepository.save(coordinator);
		Cafe mergedCafe = cafeRepository.save(cafe);

		CafeStudy cafeStudy = TestCafeStudyFactory.createCafeStudy(mergedCafe, mergedLeader, startDateTime,
			endDateTime);
		return cafeStudyRepository.save(cafeStudy);
	}

	public CafeStudy saveFinishedCafeStudy(Cafe cafe, Member coordinator, LocalDateTime startDateTime,
		LocalDateTime endDateTime) throws Exception {
		Member mergedLeader = memberRepository.save(coordinator);
		Cafe mergedCafe = cafeRepository.save(cafe);

		CafeStudy cafeStudy = TestCafeStudyFactory.createCafeStudy(mergedCafe, mergedLeader, startDateTime,
			endDateTime);

		Class<? extends CafeStudy> cafeStudyClass = cafeStudy.getClass();
		Field recruitmentStatusField = cafeStudyClass.getDeclaredField("recruitmentStatus");
		recruitmentStatusField.setAccessible(true);
		recruitmentStatusField.set(cafeStudy, RecruitmentStatus.CLOSED);

		return cafeStudyRepository.save(cafeStudy);
	}

	public CafeStudy saveCafeStudyWithName(Cafe cafe, Member coordinator, LocalDateTime startDateTime,
		LocalDateTime endDateTime, String cafeStudyName) {
		Member mergedLeader = memberRepository.save(coordinator);
		Cafe mergedCafe = cafeRepository.save(cafe);

		CafeStudy cafeStudy = TestCafeStudyFactory.createCafeStudyWithName(mergedCafe, mergedLeader, startDateTime,
			endDateTime, cafeStudyName);
		return cafeStudyRepository.save(cafeStudy);
	}

	public CafeStudy saveCafeStudyWithMemberComms(Cafe cafe, Member coordinator, LocalDateTime startDateTime,
		LocalDateTime endDateTime, MemberComms memberComms) {
		Member mergedLeader = memberRepository.save(coordinator);
		Cafe mergedCafe = cafeRepository.save(cafe);

		CafeStudy cafeStudy = TestCafeStudyFactory.createCafeStudyWithMemberComms(mergedCafe, mergedLeader,
			startDateTime, endDateTime, memberComms);
		return cafeStudyRepository.save(cafeStudy);
	}
=======

	public CafeStudy save(Cafe cafe, Member coordinator) {
		CafeStudy cafeStudy = TestCafeStudyFactory.createCafeStudy(cafe, coordinator);
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
>>>>>>> ba34f9b (test: 스터디 삭제 테스트 작성)
}
