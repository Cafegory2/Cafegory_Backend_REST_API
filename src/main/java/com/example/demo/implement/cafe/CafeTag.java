package com.example.demo.implement.cafe;

import javax.persistence.*;

import com.example.demo.implement.BaseEntity;

import com.example.demo.implement.study.CafeTagType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "deleted_date IS NULL")
@Table(name = "cafe_tag")
public class CafeTag extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "cafe_tag_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	private CafeTagType type;

	@Builder
	private CafeTag(CafeTagType type) {
		this.type = type;
	}
}
