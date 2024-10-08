package com.example.demo.helper;

import com.example.demo.factory.TestCafeCafeTagFactory;
import com.example.demo.implement.cafe.Cafe;
import com.example.demo.implement.cafe.CafeCafeTag;
import com.example.demo.implement.cafe.CafeTag;
import com.example.demo.repository.cafe.CafeCafeTagRepository;
import com.example.demo.repository.cafe.CafeRepository;
import com.example.demo.repository.cafe.CafeTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
public class CafeCafeTagSaveHelper {

    private final CafeRepository cafeRepository;
    private final CafeTagRepository cafeTagRepository;
    private final CafeCafeTagRepository cafeCafeTagRepository;

    public CafeCafeTag saveCafeCafeTag(Cafe cafe, CafeTag cafeTag) {
        Cafe mergedCafe = cafeRepository.save(cafe);
        CafeTag mergedCafeTag = cafeTagRepository.save(cafeTag);

        CafeCafeTag cafeCafeTag = TestCafeCafeTagFactory.createCafeCafeTag(mergedCafe, mergedCafeTag);
        return cafeCafeTagRepository.save(cafeCafeTag);
    }
}
