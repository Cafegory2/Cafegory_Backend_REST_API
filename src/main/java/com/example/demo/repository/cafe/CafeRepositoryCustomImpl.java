package com.example.demo.repository.cafe;

import static com.example.demo.domain.QBusinessHour.*;
import static com.example.demo.domain.QCafeImpl.*;
import static com.example.demo.domain.QMenu.*;
import static com.example.demo.domain.QReviewImpl.*;
import static com.example.demo.domain.QSnsDetail.*;

import java.util.List;

import javax.persistence.EntityManager;

import com.example.demo.domain.CafeImpl;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class CafeRepositoryCustomImpl implements CafeRepositoryCustom {

	private final EntityManager em;
	private final JPAQueryFactory queryFactory;

	public CafeRepositoryCustomImpl(EntityManager em) {
		this.em = em;
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public List<CafeImpl> findWithBasicDetails() {
		List<CafeImpl> cafes = queryFactory
			.selectFrom(cafeImpl)
			.join(cafeImpl.businessHours, businessHour)
			.join(cafeImpl.snsDetails, snsDetail)
			.join(cafeImpl.reviews, reviewImpl)
			.join(cafeImpl.menus, menu)
			.fetch();

		return cafes;

	}
}
