package com.example.demo.domain;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;
}
