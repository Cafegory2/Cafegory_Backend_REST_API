package com.example.demo.persister;


import com.example.demo.study.domain.CafeStudyTagType;
import com.example.demo.study.infrastructure.CafeStudyTagEntity;
import com.example.demo.study.infrastructure.CafeStudyTagRepository;

public class StudyTagPersister {

    private CafeStudyTagType type = CafeStudyTagType.DEVELOPMENT;

    private StudyTagPersister() {}

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
        private static CafeStudyTagRepository studyTagRepository;

        public static void init(CafeStudyTagRepository studyTagRepo) {
            studyTagRepository = studyTagRepo;
        }
    }

    public CafeStudyTagEntity save() {
        return StudyTagRepoHolder.studyTagRepository.save(build());
    }
}
