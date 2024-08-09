package com.example.demo.domain.study;

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

import com.example.demo.domain.cafe.Tag;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "cafe_study_tag")
public class CafeStudyTag {

	@Id
	@GeneratedValue
	@Column(name = "cafe_study_tag_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cafe_study_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private CafeStudy cafeStudy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tag_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Tag tag;
}
