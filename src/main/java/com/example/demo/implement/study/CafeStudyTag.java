package com.example.demo.implement.study;

import com.example.demo.implement.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "deleted_date IS NULL")
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
