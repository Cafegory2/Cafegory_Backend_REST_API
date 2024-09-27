package com.example.demo.implement.cafe;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.demo.implement.BaseEntity;

import lombok.AccessLevel;
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

	private String name;

}
