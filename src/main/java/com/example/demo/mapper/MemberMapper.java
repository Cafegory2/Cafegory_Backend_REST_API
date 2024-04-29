package com.example.demo.mapper;

import com.example.demo.domain.member.Member;
import com.example.demo.dto.WriterResponse;
import com.example.demo.dto.member.MemberResponse;
import com.example.demo.dto.study.StudyOnceSearchCommentWriterResponse;

public class MemberMapper {

	public WriterResponse toWriterResponse(Member member) {
		return new WriterResponse(member.getId(), member.getName(), member.getThumbnailImage().getThumbnailImage());
	}

	public StudyOnceSearchCommentWriterResponse toStudyOnceSearchCommentWriterResponse(Member member) {
		return new StudyOnceSearchCommentWriterResponse(member.getId(), member.getName(),
			member.getThumbnailImage().getThumbnailImage());
	}

	public MemberResponse toMemberResponse(Member member) {
		return new MemberResponse(member.getId(), member.getName(), member.getThumbnailImage().getThumbnailImage());
	}
}
