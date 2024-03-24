package com.example.demo.mapper;

import com.example.demo.domain.MemberImpl;
import com.example.demo.dto.WriterResponse;

public class MemberMapper {

	public WriterResponse toWriterResponse(MemberImpl member) {
		return new WriterResponse(member.getId(), member.getName(), member.getThumbnailImage().getThumbnailImage());
	}
}
