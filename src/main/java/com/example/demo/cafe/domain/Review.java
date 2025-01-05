package com.example.demo.cafe.domain;

import java.util.List;

import com.example.demo.domain.DateAudit;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Review {

	private ReviewId id;
	private List<CafeTagType> tags;
	private Cafe cafe;
	
	private DateAudit dateAudit;

	public boolean hasId(ReviewId id) {
		return this.id.isSameId(id);
	}
}
