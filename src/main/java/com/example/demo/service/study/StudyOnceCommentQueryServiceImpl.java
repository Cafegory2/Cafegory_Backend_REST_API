// package com.example.demo.service.study;
//
// import static com.example.demo.exception.ExceptionType.*;
//
// import java.util.List;
// import java.util.Map;
// import java.util.stream.Collectors;
//
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
//
// import com.example.demo.implement.study.CafeStudy;
// import com.example.demo.implement.study.StudyOnceComment;
// import com.example.demo.dto.study.StudyOnceCommentSearchListResponse;
// import com.example.demo.dto.study.StudyOnceCommentSearchResponse;
// import com.example.demo.exception.CafegoryException;
// import com.example.demo.mapper.MemberMapper;
// import com.example.demo.mapper.StudyOnceCommentMapper;
// import com.example.demo.repository.study.StudyOnceCommentRepository;
// import com.example.demo.repository.study.StudyOnceRepository;
//
// import lombok.RequiredArgsConstructor;
//
// @Service
// @RequiredArgsConstructor
// @Transactional(readOnly = true)
// public class StudyOnceCommentQueryServiceImpl implements StudyOnceCommentQueryService {
//
// 	private final StudyOnceCommentRepository studyOnceCommentRepository;
// 	private final StudyOnceRepository studyOnceRepository;
// 	private final MemberMapper memberMapper;
// 	private final StudyOnceCommentMapper studyOnceCommentMapper;
//
// 	@Override
// 	public StudyOnceCommentSearchListResponse searchSortedCommentsByStudyOnceId(Long studyOnceId) {
// 		List<StudyOnceComment> comments = studyOnceCommentRepository.findAllByStudyOnceId(studyOnceId);
// 		Map<Long, StudyOnceComment> commentMap = comments.stream()
// 			.collect(Collectors.toMap(StudyOnceComment::getId, comment -> comment));
//
// 		StudyOnceCommentSearchListResponse response = new StudyOnceCommentSearchListResponse(
// 			memberMapper.toStudyOnceSearchCommentWriterResponse(findStudyOnceById(studyOnceId).getLeader()));
//
// 		for (StudyOnceComment comment : comments) {
// 			StudyOnceCommentSearchResponse commentSearchResponse = new StudyOnceCommentSearchResponse();
// 			if (!comment.hasParentComment()) {
// 				commentSearchResponse.setQuestionWriter(
// 					memberMapper.toStudyOnceSearchCommentWriterResponse(comment.getMember()));
// 				commentSearchResponse.setQuestionInfo(studyOnceCommentMapper.toStudyOnceCommentInfo(comment));
// 				for (StudyOnceComment childComment : comment.getChildren()) {
// 					commentSearchResponse.addStudyOnceReplyResponse(
// 						studyOnceCommentMapper.toStudyOnceReplyResponse(childComment));
// 				}
// 				response.addStudyOnceCommentSearchResponse(commentSearchResponse);
// 			} else {
// 				StudyOnceComment parent = commentMap.get(comment.getParent().getId());
// 				commentSearchResponse.addStudyOnceReplyResponse(
// 					studyOnceCommentMapper.toStudyOnceReplyResponse(parent));
// 			}
// 		}
// 		return response;
// 	}
//
// 	private CafeStudy findStudyOnceById(long studyOnceId) {
// 		return studyOnceRepository.findById(studyOnceId)
// 			.orElseThrow(() -> new CafegoryException(STUDY_ONCE_NOT_FOUND));
// 	}
// }
