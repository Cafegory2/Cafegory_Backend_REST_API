package com.example.demo.helper;

import com.example.demo.factory.TestMenuFactory;
import com.example.demo.implement.cafe.Cafe;
import com.example.demo.implement.cafe.Menu;
import com.example.demo.repository.cafe.CafeRepository;
import com.example.demo.repository.cafe.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
public class MenuSaveHelper {

    private final MenuRepository menuRepository;
    private final CafeRepository cafeRepository;

    public Menu saveMenu(Cafe cafe) {
        Cafe mergedCafe = cafeRepository.save(cafe);

        Menu menu = TestMenuFactory.createMenu(mergedCafe);
        return menuRepository.save(menu);
    }

    public Menu saveMenu(String name, String price, Cafe cafe) {
        Cafe mergedCafe = cafeRepository.save(cafe);

        Menu menu = TestMenuFactory.createMenu(name, price, mergedCafe);
        return menuRepository.save(menu);
    }
}
