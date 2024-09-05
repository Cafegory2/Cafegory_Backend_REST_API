// package com.example.demo.mapper;
//
// import com.example.demo.implement.study.StudyOnceComment;
// import com.example.demo.dto.study.StudyOnceCommentInfo;
// import com.example.demo.dto.study.StudyOnceCommentResponse;
// import com.example.demo.dto.study.StudyOnceReplyResponse;
// import com.example.demo.dto.study.StudyOnceSearchCommentWriterResponse;
//
// public class StudyOnceCommentMapper {
//
// 	public StudyOnceCommentInfo toStudyOnceCommentInfo(StudyOnceComment comment) {
// 		return new StudyOnceCommentInfo(comment.getId(), comment.getContent());
// 	}
//
// 	public StudyOnceReplyResponse toStudyOnceReplyResponse(StudyOnceComment comment) {
// 		return new StudyOnceReplyResponse(comment.getId(), comment.getContent());
// 	}
//
// 	public StudyOnceCommentResponse toStudyOnceCommentResponse(StudyOnceComment comment,
// 		StudyOnceSearchCommentWriterResponse writerResponse) {
// 		return new StudyOnceCommentResponse(comment.getId(), comment.getContent(), writerResponse);
// 	}
// }
