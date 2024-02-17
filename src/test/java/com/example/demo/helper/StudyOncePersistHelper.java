package com.example.demo.helper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.example.demo.builder.TestStudyOnceBuilder;
import com.example.demo.domain.CafeImpl;
import com.example.demo.domain.MemberImpl;
import com.example.demo.domain.StudyOnceImpl;

public class StudyOncePersistHelper {

	@PersistenceContext
	private EntityManager em;

	public StudyOnceImpl persistDefaultStudyOnce(CafeImpl cafe, MemberImpl leader) {
		StudyOnceImpl studyOnce = new TestStudyOnceBuilder().cafe(cafe).leader(leader).build();
		em.persist(studyOnce);
		return studyOnce;
	}

}
