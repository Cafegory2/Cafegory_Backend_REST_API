package com.example.demo.controller.swaggertest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiTestController {

    @GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HelloResponse> hello(@RequestParam String name) {
        return ResponseEntity.ok(new HelloResponse("Hello, " + name));
    }

}
