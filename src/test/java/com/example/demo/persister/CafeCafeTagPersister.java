package com.example.demo.persister;

import com.example.demo.cafe.infrastructure.CafeCafeTagEntity;
import com.example.demo.cafe.infrastructure.CafeCafeTagJpaRepository;
import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.cafe.infrastructure.CafeTagEntity;

public class CafeCafeTagPersister {

	private CafeEntity cafe;
	private CafeTagEntity cafeTag;
	private int taggingCount = 0;

	private CafeCafeTagPersister() {
	}

	private CafeCafeTagPersister(CafeCafeTagPersister copy) {
		this.cafe = copy.cafe;
		this.cafeTag = copy.cafeTag;
		this.taggingCount = copy.taggingCount;
	}

	public CafeCafeTagPersister but() {
		return new CafeCafeTagPersister(this);
	}

	public static CafeCafeTagPersister aCafeCafeTag() {
		return new CafeCafeTagPersister();
	}

	public CafeCafeTagPersister withCafe(CafeEntity cafe) {
		this.cafe = cafe;
		return this;
	}

	public CafeCafeTagPersister withTag(CafeTagEntity cafeTag) {
		this.cafeTag = cafeTag;
		return this;
	}

	public CafeCafeTagPersister withTaggingCount(int taggingCount) {
		this.taggingCount = taggingCount;
		return this;
	}

	public CafeCafeTagEntity build() {
		return CafeCafeTagEntity.builder()
			.cafe(this.cafe)
			.cafeTag(this.cafeTag)
			.build();
	}

	public static class CafeCafeTagRepoHolder {
		private static CafeCafeTagJpaRepository cafeCafeTagJpaRepository;

		public static void init(CafeCafeTagJpaRepository cafeCafeTagRepo) {
			cafeCafeTagJpaRepository = cafeCafeTagRepo;
		}
	}

	public CafeCafeTagEntity persist() {
		return CafeCafeTagRepoHolder.cafeCafeTagJpaRepository.save(build());
	}
}
