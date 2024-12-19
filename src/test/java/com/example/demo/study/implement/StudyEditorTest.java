package com.example.demo.study.implement;

import static com.example.demo.study.domain.StudyRole.*;
import static com.example.demo.persister.CafeContextPersister.*;
import static com.example.demo.persister.MemberPersister.*;
import static com.example.demo.persister.StudyMemberPersister.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.persister.StudyConextPersister;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.config.ServiceTest;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.util.TimeUtil;

class StudyEditorTest extends ServiceTest {

	@Autowired
	private StudyEditor sut;

	@Autowired
	private TimeUtil timeUtil;

	@Test
	@DisplayName("카공을 삭제할 때 관련된 데이터를 같이 삭제한다.")
	void remove_study_with_cascade() {
		//given
		CafeEntity cafe = aCafe().persistWith7daysFrom9To21();
		MemberEntity coordinator = aMember().asCoordinator().persist();

		CafeStudyEntity study = StudyConextPersister.aStudy().withCafe(cafe).withMember(coordinator).persist();
		aStudyMember().withStudy(study).withMember(coordinator).withStudyRole(COORDINATOR).persist();
		//when & then
		assertDoesNotThrow(
			() -> sut.removeWithCascade(study.getId(), coordinator.getId(), timeUtil.now())
		);
	}
}