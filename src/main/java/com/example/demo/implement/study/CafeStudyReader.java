package com.example.demo.implement.study;

import org.springframework.stereotype.Component;

import com.example.demo.dto.SliceResponse;
import com.example.demo.dto.study.CafeStudySearchListRequest;
import com.example.demo.exception.CafegoryException;
import com.example.demo.exception.ExceptionType;
import com.example.demo.repository.study.CafeStudyQueryRepository;
import com.example.demo.repository.study.CafeStudyRepository;
<<<<<<< HEAD
=======

>>>>>>> d0e38a8 (feat: 스터디 삭제하는 기능 구현)
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CafeStudyReader {

<<<<<<< HEAD
    private final CafeStudyRepository cafeStudyRepository;
    private final CafeStudyQueryRepository cafeStudyQueryRepository;

    public SliceResponse<CafeStudy> searchCafeStudies(CafeStudySearchListRequest request) {
        return cafeStudyQueryRepository.findCafeStudies(request);
    }

    public CafeStudy read(Long cafeStudyId) {
        return cafeStudyRepository.findById(cafeStudyId)
            .orElseThrow(() -> new CafegoryException(ExceptionType.CAFE_STUDY_NOT_FOUND));
    }
=======
	private final CafeStudyQueryRepository cafeStudyQueryRepository;
	private final CafeStudyRepository cafeStudyRepository;

	public SliceResponse<CafeStudy> searchCafeStudies(CafeStudySearchListRequest request) {
		return cafeStudyQueryRepository.findCafeStudies(request);
	}

	public CafeStudy read(Long cafeStudyId) {
		return cafeStudyRepository.findById(cafeStudyId)
			.orElseThrow(() -> new CafegoryException(ExceptionType.CAFE_STUDY_NOT_FOUND));
	}
>>>>>>> d0e38a8 (feat: 스터디 삭제하는 기능 구현)
}
