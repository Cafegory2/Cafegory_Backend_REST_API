package com.example.demo.domain.study.implement;

import static com.example.demo.domain.study.domain.StudyRole.*;
import static com.example.demo.persister.CafeContextPersister.*;
import static com.example.demo.persister.MemberPersister.*;
import static com.example.demo.persister.StudyMemberPersister.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.config.ServiceTest;
import com.example.demo.db.cafe.CafeEntity;
import com.example.demo.db.member.MemberEntity;
import com.example.demo.db.study.CafeStudyEntity;
import com.example.demo.domain.member.domain.MemberId;
import com.example.demo.domain.study.domain.StudyId;
import com.example.demo.persister.StudyConextPersister;
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
			() -> sut.removeWithCascade(new StudyId(study.getId()), new MemberId(coordinator.getId()), timeUtil.now())
		);
	}
}