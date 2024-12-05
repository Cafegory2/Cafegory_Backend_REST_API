package com.example.demo.config;

import com.example.demo.cafe.infrastructure.CafeRepository;
import com.example.demo.repository.cafe.BusinessHourRepository;
import com.example.demo.repository.member.MemberRepository;
import com.example.demo.study.infrastructure.CafeStudyRepository;
import com.example.demo.testbuilder.CafeBuilder;
import com.example.demo.testbuilder.MemberBuilder;
import com.example.demo.testbuilder.StudyBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest()
@ActiveProfiles("test")
@Import({HelperConfig.class, DatabaseCleanup.class, FakeTimeUtil.class, SaverConfig.class})
public abstract class ServiceTest extends TestContainer {

	@Autowired
	private DatabaseCleanup databaseCleanup;
	@Autowired
	private SaverConfig saverConfig;

	@BeforeEach
	public void setUp() {
		databaseCleanup.execute();
		saverConfig.init();
	}
}
