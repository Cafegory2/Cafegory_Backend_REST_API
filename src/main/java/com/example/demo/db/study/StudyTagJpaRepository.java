package com.example.demo.db.study;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.domain.study.domain.CafeStudyTagType;

public interface StudyTagJpaRepository extends JpaRepository<CafeStudyTagEntity, Long> {

	@Query("select t.id from CafeStudyTagEntity t" +
		" where t.type in :tags")
	List<Long> countByTags(@Param("tags") List<CafeStudyTagType> tags);
}
