package com.example.demo.helper;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import com.example.demo.factory.TestCafeStudyFactory;
import com.example.demo.packageex.cafestudy.repository.CafeStudyEntity;
import com.example.demo.implement.study.MemberComms;
import com.example.demo.implement.study.RecruitmentStatus;
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

	public CafeStudyEntity saveCafeStudy(Cafe cafe, Member leader, LocalDateTime startDateTime,
										 LocalDateTime endDateTime) {
		Member mergedLeader = memberRepository.save(leader);
		Cafe mergedCafe = cafeRepository.save(cafe);

		CafeStudyEntity cafeStudyEntity = TestCafeStudyFactory.createCafeStudy(mergedCafe, mergedLeader, startDateTime,
			endDateTime);
		return cafeStudyRepository.save(cafeStudyEntity);
	}

	public CafeStudyEntity saveFinishedCafeStudy(Cafe cafe, Member leader, LocalDateTime startDateTime,
												 LocalDateTime endDateTime) throws Exception {
		Member mergedLeader = memberRepository.save(leader);
		Cafe mergedCafe = cafeRepository.save(cafe);

		CafeStudyEntity cafeStudyEntity = TestCafeStudyFactory.createCafeStudy(mergedCafe, mergedLeader, startDateTime,
			endDateTime);

		Class<? extends CafeStudyEntity> cafeStudyClass = cafeStudyEntity.getClass();
		Field recruitmentStatusField = cafeStudyClass.getDeclaredField("recruitmentStatus");
		recruitmentStatusField.setAccessible(true);
		recruitmentStatusField.set(cafeStudyEntity, RecruitmentStatus.CLOSED);

		return cafeStudyRepository.save(cafeStudyEntity);
	}

	public CafeStudyEntity saveCafeStudyWithName(Cafe cafe, Member leader, LocalDateTime startDateTime,
												 LocalDateTime endDateTime, String cafeStudyName) {
		Member mergedLeader = memberRepository.save(leader);
		Cafe mergedCafe = cafeRepository.save(cafe);

		CafeStudyEntity cafeStudyEntity = TestCafeStudyFactory.createCafeStudyWithName(mergedCafe, mergedLeader, startDateTime,
			endDateTime, cafeStudyName);
		return cafeStudyRepository.save(cafeStudyEntity);
	}

	public CafeStudyEntity saveCafeStudyWithMemberComms(Cafe cafe, Member leader, LocalDateTime startDateTime,
														LocalDateTime endDateTime, MemberComms memberComms) {
		Member mergedLeader = memberRepository.save(leader);
		Cafe mergedCafe = cafeRepository.save(cafe);

		CafeStudyEntity cafeStudyEntity = TestCafeStudyFactory.createCafeStudyWithMemberComms(mergedCafe, mergedLeader, startDateTime,
			endDateTime, memberComms);
		return cafeStudyRepository.save(cafeStudyEntity);
	}
}
