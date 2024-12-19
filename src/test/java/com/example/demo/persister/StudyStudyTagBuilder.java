package com.example.demo.persister;

import com.example.demo.study.infrastructure.CafeStudyCafeStudyTagEntity;
import com.example.demo.study.infrastructure.CafeStudyCafeStudyTagRepository;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.study.infrastructure.CafeStudyTagEntity;

public class StudyStudyTagBuilder {

    private CafeStudyEntity study;
    private CafeStudyTagEntity studyTag;

    private StudyStudyTagBuilder() {}

    private StudyStudyTagBuilder(StudyStudyTagBuilder copy) {
        this.study = copy.study;
        this.studyTag = copy.studyTag;
    }

    public StudyStudyTagBuilder but() {
        return new StudyStudyTagBuilder(this);
    }

    public static StudyStudyTagBuilder aStudyStudyTag() {
        return new StudyStudyTagBuilder();
    }

    public StudyStudyTagBuilder withStudy(CafeStudyEntity study) {
        this.study = study;
        return this;
    }

    public StudyStudyTagBuilder withTag(CafeStudyTagEntity studyTag) {
        this.studyTag = studyTag;
        return this;
    }

    public CafeStudyCafeStudyTagEntity build() {
        return CafeStudyCafeStudyTagEntity.builder()
                .cafeStudy(this.study)
                .cafeStudyTag(this.studyTag)
                .build();
    }

    public static class StudyStudyTagRepoHolder {
        private static CafeStudyCafeStudyTagRepository studyStudyTagRepository;

        public static void init(CafeStudyCafeStudyTagRepository studyStudyTagRepo) {
            studyStudyTagRepository = studyStudyTagRepo;
        }
    }

    public CafeStudyCafeStudyTagEntity save() {
        return StudyStudyTagRepoHolder.studyStudyTagRepository.save(build());
    }
}
