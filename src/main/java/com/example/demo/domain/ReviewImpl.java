package com.example.demo.domain;

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
@Table(name = "review")
public class ReviewImpl implements Review {

	@Id
	@GeneratedValue
	@Column(name = "review_id")
	private Long id;

	private String content;
	private double rate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cafe_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private CafeImpl cafe;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private MemberImpl member;

	@Override
	public void updateContent(String content) {

	}
	
	@Override
	public void updateRate(double rate) {

	}
}
