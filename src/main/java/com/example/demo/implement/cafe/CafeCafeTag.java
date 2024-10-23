package com.example.demo.implement.cafe;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.demo.implement.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "deleted_date IS NULL")
@Table(name = "cafe_cafe_tag")
public class CafeCafeTag extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "cafe_cafe_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CafeEntity cafe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_tag_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CafeTag cafeTag;

    private int taggingCount;

    @Builder
    private CafeCafeTag(CafeEntity cafe, CafeTag cafeTag) {
        this.cafe = cafe;
        this.cafeTag = cafeTag;
        this.taggingCount = 0;
    }
}
