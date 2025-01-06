package com.example.demo.domain.cafe.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Menu {

	private String name;
	private String price;
}
