package com.example.demo.repository;

import java.util.Optional;

import com.example.demo.domain.CafeImpl;

import lombok.NonNull;

public interface CafeRepositoryWrapper {
	Optional<CafeImpl> findById(@NonNull Long id);
}
