package com.example.demo.helper;

import org.springframework.transaction.annotation.Transactional;

import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.cafe.infrastructure.CafeRepository;
import com.example.demo.cafe.infrastructure.MenuEntity;
import com.example.demo.cafe.infrastructure.MenuRepository;
import com.example.demo.factory.TestMenuFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
public class MenuSaveHelper {

	private final MenuRepository menuRepository;
	private final CafeRepository cafeRepository;

	public MenuEntity saveMenu(CafeEntity cafe) {
		CafeEntity mergedCafe = cafeRepository.save(cafe);

		MenuEntity menu = TestMenuFactory.createMenu(mergedCafe);
		return menuRepository.save(menu);
	}

	public MenuEntity saveMenu(String name, String price, CafeEntity cafe) {
		CafeEntity mergedCafe = cafeRepository.save(cafe);

		MenuEntity menu = TestMenuFactory.createMenu(name, price, mergedCafe);
		return menuRepository.save(menu);
	}
}
