package com.example.demo.study.infrastructure;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.example.demo.study.domain.CafeStudyTagType;
import com.example.demo.study.domain.StudyTag;
import com.example.demo.trash.implement.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "deleted_date IS NULL")
@Table(name = "cafe_study_tag")
public class CafeStudyTagEntity extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "cafe_study_tag_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	private CafeStudyTagType type;

	@Builder
	private CafeStudyTagEntity(CafeStudyTagType type) {
		this.type = type;
	}

	public StudyTag toStudyTag() {
		return StudyTag.builder()
			.tagId(id)
			.build();
	}
}
