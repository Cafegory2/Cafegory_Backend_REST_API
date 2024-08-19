package com.example.demo.controller.swaggertest;

import lombok.Data;

@Data
public class HelloResponse {

    private String name;

    public HelloResponse() {
    }

    public HelloResponse(String name) {
        this.name = name;
    }
}
