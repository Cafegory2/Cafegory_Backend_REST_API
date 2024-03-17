package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.mapper.BusinessHourMapper;
import com.example.demo.mapper.CafeMapper;
import com.example.demo.mapper.ReviewMapper;
import com.example.demo.mapper.SnsDetailMapper;
import com.example.demo.mapper.StudyOnceMapper;

@Configuration
public class MapperConfig {

	@Bean
	public BusinessHourMapper businessHourMapper() {
		return new BusinessHourMapper();
	}

	@Bean
	public CafeMapper cafeMapper() {
		return new CafeMapper();
	}

	@Bean
	public ReviewMapper reviewMapper() {
		return new ReviewMapper();
	}

	@Bean
	public SnsDetailMapper snsDetailMapper() {
		return new SnsDetailMapper();
	}

	@Bean
	public StudyOnceMapper studyOnceMapper() {
		return new StudyOnceMapper();
	}
}
