package com.example.demo.auth.infrastructure.aws;

import com.example.demo.util.ImageData;

public interface AwsS3Client {

	void uploadImageToS3(String filename, ImageData imageDate);

	String getUrl(String filename);
}
