package com.example.demo.repository.thumbnailImage;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.member.ThumbnailImage;

public interface ThumbnailImageRepository extends JpaRepository<ThumbnailImage, Long> {
}
