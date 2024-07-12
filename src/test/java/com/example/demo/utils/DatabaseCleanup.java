package com.example.demo.utils;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.metamodel.EntityType;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DatabaseCleanup {

	@PersistenceContext
	private EntityManager em;
	private List<String> tableNames;

	@PostConstruct
	public void afterPropertiesSet() {
		tableNames = em.getMetamodel().getEntities().stream()
			.map(this::getTableName)
			.collect(Collectors.toList());
	}

	private String getTableName(EntityType<?> entityType) {
		Class<?> javaType = entityType.getJavaType();
		// @Table 어노테이션이 있는 경우 테이블 이름 가져오기
		if (javaType.isAnnotationPresent(Table.class)) {
			Table table = javaType.getAnnotation(Table.class);
			return table.name();
		}
		// @Table 어노테이션이 없는 경우 엔티티 이름에서 테이블 이름으로 변환
		return convertCamelCaseToSnakeCase(entityType.getName());
	}

	@Transactional
	public void execute() {
		em.flush();

		for (String tableName : tableNames) {
			em.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
		}
	}

	private String convertCamelCaseToSnakeCase(String input) {
		if (input == null) {
			return null;
		}
		return input.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
	}
}
