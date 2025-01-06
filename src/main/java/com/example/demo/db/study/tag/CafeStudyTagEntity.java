package com.example.demo.db.study.tag;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.example.demo.auth.implement.BaseEntity;
import com.example.demo.domain.study.domain.CafeStudyTagType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Where(clause = "deleted_date IS NULL")
@Table(name = "cafe_study_tag")
public class CafeStudyTagEntity extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "cafe_study_tag_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	private CafeStudyTagType type;

	public CafeStudyTagEntity(Long id) {
		this.id = id;
	}
}
