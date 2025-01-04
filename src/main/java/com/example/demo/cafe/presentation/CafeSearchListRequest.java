package com.example.demo.cafe.presentation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.cafe.domain.CafeTagType;
import com.example.demo.trash.dto.PagedRequest;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CafeSearchListRequest extends PagedRequest {

	private String keyword;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime openingDateTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime closingDateTime;
	@JsonProperty("cafeTags")
	private List<CafeTagType> cafeTagTypes = new ArrayList<>();

	protected CafeSearchListRequest() {
		super();
	}

	@Builder
	public CafeSearchListRequest(int page, int sizePerPage, String keyword, LocalDateTime openingDateTime,
		LocalDateTime closingDateTime, List<CafeTagType> cafeTagTypes
	) {
		super(page, sizePerPage);
		this.keyword = keyword;
		this.openingDateTime = openingDateTime;
		this.closingDateTime = closingDateTime;
		this.cafeTagTypes = cafeTagTypes;
	}

	public void applyDefaultOpeningTime(LocalDateTime openingDateTime) {
		if (this.openingDateTime == null) {
			this.openingDateTime = openingDateTime;
		}
	}

	public void applyDefaultClosingTime(LocalDateTime closingDateTime) {
		if (this.closingDateTime == null) {
			this.closingDateTime = closingDateTime;
		}
	}
}
