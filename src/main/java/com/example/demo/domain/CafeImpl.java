package com.example.demo.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Table(name = "cafe")
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

	private int maxAllowableStay;
	private double avgReviewRate;

	@OneToMany(mappedBy = "cafe")
	private List<BusinessHour> businessHours = new ArrayList<>();

	@OneToMany(mappedBy = "cafe")
	private List<SnsDetail> snsDetails = new ArrayList<>();

	@Override
	public String showFullAddress() {
		return null;
	}

}
