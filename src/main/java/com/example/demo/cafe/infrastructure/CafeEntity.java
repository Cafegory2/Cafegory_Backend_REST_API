package com.example.demo.cafe.infrastructure;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.example.demo.cafe.domain.Cafe;
import com.example.demo.trash.implement.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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
	private Address address;

	private String sns;

	@OneToMany(mappedBy = "cafe")
	private List<CafeKeywordEntity> cafeKeywords = new ArrayList<>();

	@OneToMany(mappedBy = "cafe")
	private List<CafeCafeTagEntity> cafeCafeTags = new ArrayList<>();

	@OneToMany(mappedBy = "cafe")
	private List<MenuEntity> menus = new ArrayList<>();

	@Builder
	private CafeEntity(String name, String mainImageUrl, Address address, String sns) {
		this.name = name;
		this.mainImageUrl = mainImageUrl;
		this.address = address;
		this.sns = sns;
	}

	public Cafe toCafe() {
		return Cafe.builder()
			.id(this.id)
			.name(this.name)
			.imgUrl(this.mainImageUrl)
			.build();
	}
}
