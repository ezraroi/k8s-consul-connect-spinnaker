package com.example.frontend;

import com.example.backend.HelloRequest;
import com.example.backend.MyServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
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
    private final RestTemplate notLoadBalancedRestTemplate;

    @GrpcClient("myService")
    private MyServiceGrpc.MyServiceBlockingStub myServiceStub;

    public SimpleController(RestTemplate restTemplate,
                            @Value("${backend.service.url}") String url,
                            @Value("${HOSTNAME:127.0.0.1}") String hostname,
                            @Value("${conf.value}") String conf,
                            RestTemplateBuilder builder) {
        this.restTemplate = restTemplate;
        this.backendUrl = url;
        this.hostname = hostname;
        this.conf = conf;
        this.notLoadBalancedRestTemplate = builder.build();
    }

    @GetMapping("/")
    public String homePage(Model model) {
        log.info("Returning page");
        model.addAttribute("appVersion", 1);
        model.addAttribute("restEnvoy", getEnvoyMessage());
        model.addAttribute("restClient", getClientSideLBMessage());
        model.addAttribute("grpc", getGRPCMessage());
        model.addAttribute("hostname", hostname);
        model.addAttribute("conf", conf);
        return "home";
    }

    private String getEnvoyMessage() {
        String url = this.backendUrl + "/hello";
        return getHttpResponse(url, false);
    }

    private String getClientSideLBMessage() {
        return getHttpResponse("http://backend/hello", true);
    }

    private String getHttpResponse(String url, boolean loadBalanced) {
        String message = "";
        log.info("REST URL: " + url);
        try {
            ResponseEntity<String> response;
            if (loadBalanced) {
                response = restTemplate.getForEntity(url, String.class);
            } else {
                response = notLoadBalancedRestTemplate.getForEntity(url, String.class);
            }
            log.info("Got response code: " + response.getStatusCode());
            message = response.getBody();
        } catch (Exception exception) {
            log.error("Failed to connect to backend via REST", exception);
            message = "Failed to connect to backend service via REST";
        }
        return message;
    }

    private String getGRPCMessage() {
        String gRPC = "";
        try {
            HelloRequest request = HelloRequest.newBuilder()
                    .setName("Frontend")
                    .build();
            gRPC = myServiceStub.sayHello(request).getMessage();
        } catch (Exception exception) {
            log.error("Failed to connect to backend via gRPC", exception);
            gRPC = "Failed to connect to backend service via gRPC";
        }
        return gRPC;
    }
}