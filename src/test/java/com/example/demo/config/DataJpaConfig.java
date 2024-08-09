//package com.example.demo.config;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//
//import com.example.demo.repository.cafe.CafeQueryDslRepository;
//import com.example.demo.repository.study.StudyMemberRepositoryCustom;
//import com.example.demo.repository.study.StudyMemberRepositoryCustomImpl;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//
//@TestConfiguration
//public class DataJpaConfig {
//
//	@PersistenceContext
//	private EntityManager em;
//
//	@Bean
//	public JPAQueryFactory jpaQueryFactory() {
//		return new JPAQueryFactory(em);
//	}
//
//	@Bean
//	public StudyMemberRepositoryCustom studyMemberRepositoryCustom() {
//		return new StudyMemberRepositoryCustomImpl(jpaQueryFactory());
//	}
//
//	@Bean
//	public CafeQueryDslRepository cafeQueryDslRepository() {
//		return new CafeQueryDslRepository(jpaQueryFactory());
//	}
//
//	@Bean
//	public DatabaseCleanup databaseCleanup() {
//		return new DatabaseCleanup();
//	}
//}
