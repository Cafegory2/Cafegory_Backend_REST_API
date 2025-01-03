package com.example.demo.study.infrastructure;

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

import org.hibernate.annotations.Where;

import com.example.demo.trash.implement.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "deleted_date IS NULL")
@Table(name = "cafe_study_cafe_study_tag")
public class
CafeStudyCafeStudyTagEntity extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "cafe_study_cafe_study_tag_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cafe_study_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private CafeStudyEntity cafeStudy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cafe_study_tag_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private CafeStudyTagEntity cafeStudyTag;

	public CafeStudyCafeStudyTagEntity(Long studyId, Long studyTagId) {
		this.cafeStudy = new CafeStudyEntity(studyId);
		this.cafeStudyTag = new CafeStudyTagEntity(studyTagId);
	}

	@Builder
	private CafeStudyCafeStudyTagEntity(CafeStudyEntity cafeStudy, CafeStudyTagEntity cafeStudyTag) {
		this.cafeStudy = cafeStudy;
		this.cafeStudyTag = cafeStudyTag;
	}
}
