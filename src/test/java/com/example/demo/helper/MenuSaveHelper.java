package com.example.demo.helper;

import com.example.demo.factory.TestMenuFactory;
import com.example.demo.implement.cafe.CafeEntity;
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

    public Menu saveMenu(CafeEntity cafeEntity) {
        CafeEntity mergedCafeEntity = cafeRepository.save(cafeEntity);

        Menu menu = TestMenuFactory.createMenu(mergedCafeEntity);
        return menuRepository.save(menu);
    }

    public Menu saveMenu(String name, String price, CafeEntity cafeEntity) {
        CafeEntity mergedCafeEntity = cafeRepository.save(cafeEntity);

        Menu menu = TestMenuFactory.createMenu(name, price, mergedCafeEntity);
        return menuRepository.save(menu);
    }
}
