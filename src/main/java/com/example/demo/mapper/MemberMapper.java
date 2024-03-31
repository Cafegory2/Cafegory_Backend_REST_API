package com.example.demo.mapper;

import com.example.demo.domain.MemberImpl;
import com.example.demo.dto.MemberResponse;
import com.example.demo.dto.WriterResponse;

public class MemberMapper {

	public WriterResponse toWriterResponse(MemberImpl member) {
		return new WriterResponse(member.getId(), member.getName(), member.getThumbnailImage().getThumbnailImage());
	}

	public MemberResponse toMemberResponse(MemberImpl member) {
		return new MemberResponse(member.getId(), member.getName(), member.getThumbnailImage().getThumbnailImage());
	}
}
