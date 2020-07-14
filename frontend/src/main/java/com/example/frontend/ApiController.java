package com.example.frontend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ApiController {

    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

    private final RestTemplate restTemplate;
    private final String backendUrl;

    public ApiController(RestTemplateBuilder builder, @Value("${backend.service.url}") String hostname) {
        this.restTemplate = builder.build();
        this.backendUrl = hostname;
    }

    @GetMapping("/api/headers")
    public void getHeaders() {
        ResponseEntity<String> response;
        response = restTemplate.getForEntity(this.backendUrl + "/headers", String.class);
        log.info("Got response code: " + response.getStatusCode());

    }

    @GetMapping("/api/db")
    public String getEntries() {
        ResponseEntity<String> response;
        response = restTemplate.getForEntity(this.backendUrl + "/all", String.class);
        log.info("Got response code: " + response.getStatusCode());
        return response.getBody();
    }

}
