package com.example.demo.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.example.demo.helper.CafePersistHelper;
import com.example.demo.helper.MemberPersistHelper;
import com.example.demo.helper.ReviewPersistHelper;
import com.example.demo.helper.StudyMemberPersistHelper;
import com.example.demo.helper.StudyOnceCommentPersistHelper;
import com.example.demo.helper.StudyOncePersistHelper;
import com.example.demo.helper.ThumbnailImagePersistHelper;
import com.example.demo.helper.entitymanager.RealEntityManagerAdaptor;
import com.example.demo.helper.save.CafeSaveHelper;
import com.example.demo.helper.save.MemberSaveHelper;
import com.example.demo.helper.save.ReviewSaveHelper;
import com.example.demo.helper.save.StudyMemberSaveHelper;
import com.example.demo.helper.save.StudyOnceCommentSaveHelper;
import com.example.demo.helper.save.StudyOnceSaveHelper;
import com.example.demo.helper.save.ThumbnailImageSaveHelper;

@TestConfiguration
public class TestConfig {

	@Bean
	public ThumbnailImagePersistHelper thumbnailImagePersistHelper() {
		return new ThumbnailImagePersistHelper(realEntityManagerAdaptor());
	}

	@Bean
	public MemberPersistHelper memberPersistHelper() {
		return new MemberPersistHelper(realEntityManagerAdaptor());
	}

	@Bean
	public CafePersistHelper cafePersistHelper() {
		return new CafePersistHelper(realEntityManagerAdaptor());
	}

	@Bean
	public ReviewPersistHelper reviewPersistHelper() {
		return new ReviewPersistHelper(realEntityManagerAdaptor());
	}

	@Bean
	public StudyMemberPersistHelper studyMemberPersistHelper() {
		return new StudyMemberPersistHelper(realEntityManagerAdaptor());
	}

	@Bean
	public StudyOncePersistHelper studyOncePersistHelper() {
		return new StudyOncePersistHelper(realEntityManagerAdaptor());
	}

	@Bean
	public StudyOnceCommentPersistHelper studyOnceCommentPersistHelper() {
		return new StudyOnceCommentPersistHelper(realEntityManagerAdaptor());
	}

	@Bean
	public RealEntityManagerAdaptor realEntityManagerAdaptor() {
		return new RealEntityManagerAdaptor();
	}

	///////////////
	@Bean
	public CafeSaveHelper cafeSaveHelper() {
		return new CafeSaveHelper();
	}

	@Bean
	public MemberSaveHelper memberSaveHelper() {
		return new MemberSaveHelper();
	}

	@Bean
	public ReviewSaveHelper reviewSaveHelper() {
		return new ReviewSaveHelper();
	}

	@Bean
	public StudyMemberSaveHelper studyMemberSaveHelper() {
		return new StudyMemberSaveHelper();
	}

	@Bean
	public StudyOnceCommentSaveHelper studyOnceCommentSaveHelper() {
		return new StudyOnceCommentSaveHelper();
	}

	@Bean
	public StudyOnceSaveHelper studyOnceSaveHelper() {
		return new StudyOnceSaveHelper();
	}

	@Bean
	public ThumbnailImageSaveHelper thumbnailImageSaveHelper() {
		return new ThumbnailImageSaveHelper();
	}

}
