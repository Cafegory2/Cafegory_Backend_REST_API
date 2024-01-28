package com.example.demo.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
// @Builder
// @AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "cafe")
// @ToString(of = {"id", "name"})
public class CafeImpl implements Cafe {

	@Id
	@GeneratedValue
	@Column(name = "cafe_id")
	private Long id;

	private String name;

	@Embedded
	private Address address;

	@Transient
	private boolean isOpen;
	private String phone;

	@Enumerated(EnumType.STRING)
	private MaxAllowableStay maxAllowableStay;
	private double avgReviewRate;
	private boolean isAbleToStudy;
	private int minBeveragePrice;

	// @Enumerated(EnumType.STRING)
	// private MinMenuPrice minBeveragePrice;

	@OneToMany(mappedBy = "cafe")
	private List<BusinessHour> businessHours = new ArrayList<>();

	@OneToMany(mappedBy = "cafe")
	private List<SnsDetail> snsDetails = new ArrayList<>();

	@OneToMany(mappedBy = "cafe")
	private List<ReviewImpl> reviews = new ArrayList<>();

	@OneToMany(mappedBy = "cafe")
	private List<Menu> menus = new ArrayList<>();

	@Builder
	public CafeImpl(Long id, String name, Address address, boolean isOpen, String phone,
		MaxAllowableStay maxAllowableStay,
		double avgReviewRate, boolean isAbleToStudy, int minBeveragePrice
		// , MinMenuPrice minMenuPrice
	) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.isOpen = isOpen;
		this.phone = phone;
		this.maxAllowableStay = maxAllowableStay;
		this.avgReviewRate = avgReviewRate;
		this.isAbleToStudy = isAbleToStudy;
		this.minBeveragePrice = minBeveragePrice;
		// this.minBeveragePrice = minMenuPrice;
	}

	@Override
	public String showFullAddress() {

		return null;
	}

	public void addBusinessHour(BusinessHour businessHour) {
		businessHours.add(businessHour);
		businessHour.setCafe(this);
	}

	public void addSnsDetail(SnsDetail snsDetail) {
		snsDetails.add(snsDetail);
		snsDetail.setCafe(this);
	}

	public void addReview(ReviewImpl review) {
		reviews.add(review);
		review.setCafe(this);
	}

	public void addMenu(Menu menu) {
		menus.add(menu);
		menu.setCafe(this);
	}

}
