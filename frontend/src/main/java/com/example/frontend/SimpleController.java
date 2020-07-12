package com.example.frontend;

import com.example.backend.HelloRequest;
import com.example.backend.MyServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
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
    private final String conf;
    private final RestTemplate restTemplate;

    @GrpcClient("myService")
    private MyServiceGrpc.MyServiceBlockingStub myServiceStub;

    public SimpleController(RestTemplate restTemplate,
                            @Value("${backend.service.url}") String url,
                            @Value("${HOSTNAME:127.0.0.1}") String hostname,
                            @Value("${conf.value}") String conf) {
        this.restTemplate = restTemplate;
        this.backendUrl = url;
        this.hostname = hostname;
        this.conf = conf;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        log.info("Returning page");
        model.addAttribute("appVersion", 1);
        String url = this.backendUrl + "/hello";
        String message = "";
        String gRPC = "";
        try {
            ResponseEntity<String> response
                    = restTemplate.getForEntity(url, String.class);
            log.info("Got response code: " + response.getStatusCode());
            message = response.getBody();
        } catch (Exception exception) {
            log.error("Failed to connect to backend", exception);
            message = "Failed to connect to backend service";
        }

        HelloRequest request = HelloRequest.newBuilder()
                .setName("Frontend")
                .build();
        gRPC = myServiceStub.sayHello(request).getMessage();

        model.addAttribute("message", message);
        model.addAttribute("hostname", hostname);
        model.addAttribute("conf", conf);
        model.addAttribute("grpc", gRPC);
        return "home";
    }
}