package com.example.demo.db.cafe.review;

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

import com.example.demo.auth.implement.BaseEntity;
import com.example.demo.db.cafe.tag.CafeTagEntity;

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
@Table(name = "review_cafe_tag")
public class ReviewCafeTagEntity extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "review_cafe_tag_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ReviewEntity review;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cafe_tag_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private CafeTagEntity cafeTag;
}
