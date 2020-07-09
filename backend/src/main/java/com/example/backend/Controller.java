package com.example.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Value("${conf.value}")
    private String conf;

    @GetMapping("/hello")
    public String getMessage() {
        return "Hello from version 1";
    }

    @GetMapping("/conf")
    public String getConf() {
        return conf;
    }
}
