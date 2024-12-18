package com.example.demo.testbuilder;


import com.example.demo.study.domain.CafeStudyTagType;
import com.example.demo.study.infrastructure.CafeStudyTagEntity;
import com.example.demo.study.infrastructure.CafeStudyTagRepository;

public class StudyTagBuilder {

    private CafeStudyTagType type = CafeStudyTagType.DEVELOPMENT;

    private StudyTagBuilder() {}

    private StudyTagBuilder(StudyTagBuilder copy) {
        this.type = copy.type;
    }

    public StudyTagBuilder but() {
        return new StudyTagBuilder(this);
    }

    public static StudyTagBuilder aTag() {
        return new StudyTagBuilder();
    }

    public StudyTagBuilder withType(CafeStudyTagType type) {
        this.type = type;
        return this;
    }

    public CafeStudyTagEntity build() {
        return CafeStudyTagEntity.builder()
                .type(this.type)
                .build();
    }

    public static class StudyTagSaver {
        private static CafeStudyTagRepository studyTagRepository;

        public static void init(CafeStudyTagRepository studyTagRepo) {
            studyTagRepository = studyTagRepo;
        }
    }

    public CafeStudyTagEntity save() {
        return StudyTagSaver.studyTagRepository.save(build());
    }
}
