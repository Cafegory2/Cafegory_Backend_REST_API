package com.example.demo.implement.study;

import com.example.demo.implement.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "cafe_study_tag")
public class CafeStudyTag extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "cafe_study_tag_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private CafeStudyTagType type;

    @Builder
    private CafeStudyTag(CafeStudyTagType type) {
        this.type = type;
    }
}
