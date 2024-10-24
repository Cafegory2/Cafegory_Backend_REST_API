package com.example.demo.helper;

import com.example.demo.factory.TestCafeCafeTagFactory;
import com.example.demo.implement.cafe.CafeEntity;
import com.example.demo.implement.cafe.CafeCafeTagEntity;
import com.example.demo.implement.cafe.CafeTagEntity;
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

    public CafeCafeTagEntity saveCafeCafeTag(CafeEntity cafe, CafeTagEntity cafeTag) {
        CafeEntity mergedCafe = cafeRepository.save(cafe);
        CafeTagEntity mergedCafeTag = cafeTagRepository.save(cafeTag);

        CafeCafeTagEntity cafeCafeTag = TestCafeCafeTagFactory.createCafeCafeTag(mergedCafe, mergedCafeTag);
        return cafeCafeTagRepository.save(cafeCafeTag);
    }
}
