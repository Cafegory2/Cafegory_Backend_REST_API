package com.example.demo.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ImageDownloadUtil {

    public static ImageData downloadImage(String imageUrl) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> response = restTemplate.exchange(
                imageUrl, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), byte[].class);

        return new ImageData(response.getBody(), response.getHeaders().getContentType().toString());
    }

}
