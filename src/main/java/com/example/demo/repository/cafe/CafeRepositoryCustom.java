package com.example.demo.repository.cafe;

import java.util.List;

import com.example.demo.domain.CafeImpl;

public interface CafeRepositoryCustom {

	List<CafeImpl> findWithBasicDetails();

}
