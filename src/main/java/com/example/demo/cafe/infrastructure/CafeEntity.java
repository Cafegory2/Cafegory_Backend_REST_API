package com.example.demo.cafe.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.example.demo.auth.implement.BaseEntity;
import com.example.demo.cafe.domain.Address;
import com.example.demo.cafe.domain.Cafe;
import com.example.demo.cafe.domain.CafeId;
import com.example.demo.cafe.domain.Menu;
import com.example.demo.study.infrastructure.CafeStudyEntity;

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
@Table(name = "cafe")
public class CafeEntity extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "cafe_id")
	private Long id;

	private String name;

	private String mainImageUrl;

	@Embedded
	private AddressEmbeddable address;

	private String sns;

	@OneToMany(mappedBy = "cafe")
	private List<CafeKeywordEntity> cafeKeywords = new ArrayList<>();

	@OneToMany(mappedBy = "cafe")
	private List<CafeCafeTagEntity> cafeCafeTags = new ArrayList<>();

	@OneToMany(mappedBy = "cafe")
	private List<MenuEntity> menus = new ArrayList<>();

	//TODO: 확인하기 - doha
	@OneToMany(mappedBy = "cafe")
	private List<CafeStudyEntity> cafeStudies = new ArrayList<>();

	public CafeEntity(Long id) {
		this.id = id;
	}

	public Cafe toCafe() {
		return Cafe.builder()
			.id(new CafeId(this.id))
			.name(this.name)
			.imgUrl(this.mainImageUrl)
			.sns(this.sns)
			.cafeTagTypes(
				this.cafeCafeTags.stream()
					.map(CafeCafeTagEntity::getCafeTag)
					.filter(Objects::nonNull)
					.map(CafeTagEntity::getType)
					.collect(Collectors.toList())
			)
			.address(
				Address.builder()
					.fullAddress(this.address.getFullAddress())
					.region(this.address.getRegion())
					.build()
			)
			.menus(
				this.menus.stream()
					.map(menu ->
						Menu.builder()
							.name(menu.getName())
							.price(menu.getPrice())
							.build()
					)
					.collect(Collectors.toList())
			)
			.build();
	}
}
