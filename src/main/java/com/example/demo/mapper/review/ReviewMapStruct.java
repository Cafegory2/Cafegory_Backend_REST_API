package com.example.demo.mapper.review;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import com.example.demo.domain.ReviewImpl;
import com.example.demo.dto.ReviewResponse;
import com.example.demo.dto.ReviewSearchResponse;

@Mapper
public interface ReviewMapStruct {

	ReviewMapStruct INSTANCE = Mappers.getMapper(ReviewMapStruct.class);

	/* 첫번째 방식
	@Mapping(source = "id", target = "reviewId")
	// @Mapping(source = "member", target = "writer", qualifiedByName = "memberToWriterResponse")
	@Mapping(source = "member", target = "writer")
	ReviewResponse reviewToReviewResponse(ReviewImpl review);

	// @Named("memberToWriterResponse") 다양한 mapping 방식을 사용할때 씀 qualifiedByName이 없어도 알아서 해줌
	default WriterResponse memberToWriterResponse(MemberImpl member) {
		return new WriterResponse(
			member.getId(),
			member.getName(),
			member.getThumbnailImage() != null ? member.getThumbnailImage().getThumbnailImage() : null
		);
	}
	 */
	@Mapping(source = "id", target = "reviewId")
	@Mapping(source = "member.id", target = "writer.memberId")
	@Mapping(source = "member.name", target = "writer.name")
	@Mapping(source = "member.thumbnailImage.thumbnailImage", target = "writer.thumbnailImg")
	ReviewResponse reviewToReviewResponse(ReviewImpl review);

	// @Named("memberToWriterResponse") 다양한 mapping 방식을 사용할때 씀 qualifiedByName이 없어도 알아서 해줌
	// default WriterResponse memberToWriterResponse(MemberImpl member) {
	// 	return new WriterResponse(
	// 		member.getId(),
	// 		member.getName(),
	// 		member.getThumbnailImage() != null ? member.getThumbnailImage().getThumbnailImage() : null
	// 	);
	// }

	@Mapping(source = "id", target = "reviewId")
	@Mapping(source = "member.id", target = "writer.memberId")
	@Mapping(source = "member.name", target = "writer.name")
	@Mapping(source = "member.thumbnailImage.thumbnailImage", target = "writer.thumbnailImg")
	ReviewSearchResponse reviewToReviewSearchResponse(ReviewImpl review);

	default List<ReviewSearchResponse> pagedReviewsToReviewSearchResponses(Page<ReviewImpl> pagedReviews) {
		return pagedReviews.getContent().stream()
			.map(this::reviewToReviewSearchResponse)
			.collect(Collectors.toList());
	}
}
