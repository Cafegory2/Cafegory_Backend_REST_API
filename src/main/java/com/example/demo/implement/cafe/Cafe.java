package com.example.demo.implement.cafe;

import javax.persistence.*;

import com.example.demo.implement.BaseEntity;

import com.example.demo.implement.study.CafeStudyMember;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "cafe")
public class Cafe extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "cafe_id")
	private Long id;

	private String name;

	private String mainImageUrl;

	@Embedded
	private Address address;

	private String sns;

	@OneToMany(mappedBy = "cafe")
	private List<CafeKeyword> cafeKeywords = new ArrayList<>();

	@Builder
	private Cafe(String name, String mainImageUrl, Address address, String sns) {
		this.name = name;
		this.mainImageUrl = mainImageUrl;
		this.address = address;
		this.sns = sns;
	}

	//	public String showFullAddress() {
	//		return address.showFullAddress();
	//	}
	//
	//	public String getRegion() {
	//		return this.address.getRegion();
	//	}
	//
	//	public boolean isOpen(OpenChecker<BusinessHour> openChecker) {
	//		return openChecker.checkWithBusinessHours(this.businessHours, LOCAL_DATE_TIME_NOW);
	//	}
	//
	//	public OptionalDouble calcAverageRating() {
	//		return reviews.stream()
	//			.mapToDouble(Review::getRate)
	//			.average();
	//	}
	//
	//	public BusinessHour findBusinessHour(DayOfWeek dayOfWeek) {
	//		return businessHours.stream()
	//			.filter(businessHour -> businessHour.matchesDayOfWeek(dayOfWeek))
	//			.findFirst()
	//			.orElseThrow(() -> new CafegoryException(CAFE_NOT_FOUND_DAY_OF_WEEK));
	//	}
	//
	//	public void changeBusinessHours(List<BusinessHour> businessHours) {
	//		this.businessHours = businessHours;
	//	}
}
