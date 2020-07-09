package com.example.frontend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class SimpleController {

    private static final Logger log = LoggerFactory.getLogger(SimpleController.class);

    private final String backendUrl;
    private final String hostname;
    private final RestTemplate restTemplate;

    public SimpleController(RestTemplate restTemplate,
                            @Value("${backend.service.url}") String url, @Value("${HOSTNAME:127.0.0.1}") String hostname) {
        this.restTemplate = restTemplate;
        this.backendUrl = url;
        this.hostname = hostname;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        log.info("Returning page");
        model.addAttribute("appVersion", 1);
        String url = this.backendUrl + "/hello";
        String message = "";
        try {
            ResponseEntity<String> response
                    = restTemplate.getForEntity(url, String.class);
            log.info("Got response code: " + response.getStatusCode());
            message = response.getBody();
        } catch (Exception exception) {
            log.error("Failed to connect to backend", exception);
            message = "Failed to connect to backend service";
        }
        model.addAttribute("message", message);
        model.addAttribute("hostname", hostname);
        return "home";
    }
}