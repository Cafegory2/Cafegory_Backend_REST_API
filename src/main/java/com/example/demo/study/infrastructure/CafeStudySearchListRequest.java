package com.example.demo.study.infrastructure;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.cafe.domain.CafeTagType;
import com.example.demo.study.domain.CafeStudyTagType;
import com.example.demo.study.domain.MemberComms;
import com.example.demo.auth.dto.PagedRequest;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CafeStudySearchListRequest extends PagedRequest {

	@NotBlank
	private String keyword;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	@JsonProperty("cafeStudyTag")
	private CafeStudyTagType cafeStudyTagType;
	@JsonProperty("cafeTags")
	private List<CafeTagType> cafeTagTypes = new ArrayList<>();
	private MemberComms memberComms;

	protected CafeStudySearchListRequest() {
		super();
	}

	@Builder
	private CafeStudySearchListRequest(int page, int sizePerPage, String keyword,
		LocalDate date, CafeStudyTagType cafeStudyTagType,
		List<CafeTagType> cafeTagTypes, MemberComms memberComms) {
		super(page, sizePerPage);
		this.keyword = keyword;
		this.date = date;
		this.cafeStudyTagType = cafeStudyTagType;
		this.cafeTagTypes = cafeTagTypes;
		this.memberComms = memberComms;
	}

}
