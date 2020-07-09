package com.example.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private static final Logger log = LoggerFactory.getLogger(Controller.class);

    @Value("${conf.value}")
    private String conf;

    @Value("${HOSTNAME:127.0.0.1}")
    private String hostname;

    @GetMapping("/hello")
    public String getMessage() {
        log.info("Api hello called");
        return "Hello from version 1 on " + hostname;
    }

    @GetMapping("/conf")
    public String getConf() {
        log.info("Api conf called");
        return conf;
    }
}
