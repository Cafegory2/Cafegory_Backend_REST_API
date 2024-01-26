package com.example.demo.repository.cafe;

import java.util.List;

import com.example.demo.domain.CafeImpl;
import com.example.demo.service.dto.CafeSearchCondition;

public interface CafeRepositoryCustom {

	List<CafeImpl> findWithDynamicFilter(CafeSearchCondition searchCondition);

}
