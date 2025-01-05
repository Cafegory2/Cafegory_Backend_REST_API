package com.example.demo.cafe.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Cafe {

	private CafeId id;
	private String name;
	private String imgUrl;
	private String sns;
	private List<CafeTagType> cafeTagTypes = new ArrayList<>();
	private Address address;
	private List<Menu> menus;

	public boolean hasId(CafeId id) {
		return this.id.isSameId(id);
	}
}
