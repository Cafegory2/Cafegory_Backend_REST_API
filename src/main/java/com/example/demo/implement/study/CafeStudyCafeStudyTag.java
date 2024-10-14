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
@Table(name = "cafe_study_cafe_study_tag")
public class CafeStudyCafeStudyTag extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "cafe_study_cafe_study_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_study_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CafeStudy cafeStudy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_study_tag_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CafeStudyTag cafeStudyTag;

    @Builder
    private CafeStudyCafeStudyTag(CafeStudy cafeStudy, CafeStudyTag cafeStudyTag) {
        this.cafeStudy = cafeStudy;
        this.cafeStudyTag = cafeStudyTag;
    }
}
