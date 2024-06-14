package com.example.demo.helper;

import com.example.demo.domain.member.ThumbnailImage;
import com.example.demo.factory.TestThumbnailImageFactory;
import com.example.demo.repository.thumbnailImage.ThumbnailImageRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ThumbnailImageSaveHelper {

	private final ThumbnailImageRepository thumbnailImageRepository;

	public ThumbnailImage persistDefaultThumbnailImage() {
		ThumbnailImage thumbnailImage = TestThumbnailImageFactory.createThumbnailImage();
		return thumbnailImageRepository.save(thumbnailImage);
	}
}
