package com.example.demo.service.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.demo.util.ImageData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
@RequiredArgsConstructor
public class AwsService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.cloud-front-domain}")
    private String cloudFrontDomain;

    private final AmazonS3 s3client;

    public void uploadImageToS3(String filename, ImageData imageDate) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(imageDate.getContentLength());
        objectMetadata.setContentType(imageDate.getContentType());

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageDate.getBytes());

        s3client.putObject(new PutObjectRequest(bucket, filename, byteArrayInputStream, objectMetadata));
    }

    public String getUrl(String filename) {
        return cloudFrontDomain + "/" + filename;
    }
}
