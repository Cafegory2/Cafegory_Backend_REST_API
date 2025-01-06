package com.example.demo.persister;

import com.example.demo.db.study.CafeStudyTagEntity;
import com.example.demo.db.study.StudyTagJpaRepository;
import com.example.demo.domain.study.domain.CafeStudyTagType;

public class StudyTagPersister {

	private CafeStudyTagType type = CafeStudyTagType.DEVELOPMENT;

	private StudyTagPersister() {
	}

	private StudyTagPersister(StudyTagPersister copy) {
		this.type = copy.type;
	}

	public StudyTagPersister but() {
		return new StudyTagPersister(this);
	}

	public static StudyTagPersister aTag() {
		return new StudyTagPersister();
	}

	public StudyTagPersister withType(CafeStudyTagType type) {
		this.type = type;
		return this;
	}

	public CafeStudyTagEntity build() {
		return CafeStudyTagEntity.builder()
			.type(this.type)
			.build();
	}

	public static class StudyTagRepoHolder {
		private static StudyTagJpaRepository studyTagRepository;

		public static void init(StudyTagJpaRepository studyTagRepo) {
			studyTagRepository = studyTagRepo;
		}
	}

	public CafeStudyTagEntity persist() {
		return StudyTagRepoHolder.studyTagRepository.save(build());
	}
}
