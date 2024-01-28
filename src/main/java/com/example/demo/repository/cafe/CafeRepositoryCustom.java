package com.example.demo.repository.cafe;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.example.demo.domain.CafeImpl;
import com.example.demo.service.dto.CafeSearchCondition;

public interface CafeRepositoryCustom {

	List<CafeImpl> findWithDynamicFilterAndNoPaging(CafeSearchCondition searchCondition);

	List<CafeImpl> findWithDynamicFilter(CafeSearchCondition searchCondition, Pageable pageable);
}
