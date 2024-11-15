package com.example.demo.cafe.infrastructure;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.example.demo.cafe.domain.CafeTagType;
import com.example.demo.trash.implement.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "deleted_date IS NULL")
@Table(name = "cafe_tag")
public class CafeTagEntity extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "cafe_tag_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	private CafeTagType type;

	@Builder
	private CafeTagEntity(CafeTagType type) {
		this.type = type;
	}
}
