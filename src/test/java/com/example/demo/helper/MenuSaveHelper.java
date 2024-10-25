package com.example.demo.helper;

import com.example.demo.factory.TestMenuFactory;
import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.cafe.infrastructure.MenuEntity;
import com.example.demo.cafe.infrastructure.CafeRepository;
import com.example.demo.repository.cafe.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

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
