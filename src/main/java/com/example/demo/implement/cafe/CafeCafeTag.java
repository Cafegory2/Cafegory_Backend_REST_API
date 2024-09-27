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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "cafe_cafe_tag")
public class CafeCafeTag extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "cafe_cafe_tag_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cafe_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Cafe cafe;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cafe_tag_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private CafeTag cafeTag;

	private int taggingCount;
}