package com.example.demo.helper;

import com.example.demo.builder.TestThumbnailImageBuilder;
import com.example.demo.domain.member.ThumbnailImage;
import com.example.demo.repository.thumbnailImage.ThumbnailImageRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ThumbnailImageSaveHelper {

	private final ThumbnailImageRepository thumbnailImageRepository;

	public ThumbnailImage persistDefaultThumbnailImage() {
		ThumbnailImage thumbnailImage = new TestThumbnailImageBuilder().build();
		return thumbnailImageRepository.save(thumbnailImage);
	}
}
