package com.example.demo.implement.cafe;

import javax.persistence.*;

import com.example.demo.implement.BaseEntity;

import com.example.demo.implement.study.CafeTagType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "cafe_tag")
public class CafeTag extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "cafe_tag_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	private CafeTagType type;

	@Builder
	public CafeTag(CafeTagType type) {
		this.type = type;
	}
}
