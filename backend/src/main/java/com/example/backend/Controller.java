package com.example.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;

@RestController
public class Controller {

    private static final Logger log = LoggerFactory.getLogger(Controller.class);

    @Value("${conf.value}")
    private String conf;

    @Value("${HOSTNAME:127.0.0.1}")
    private String hostname;

    @Autowired
    private UserRepository userRepository;

    private final Random random =  new Random();

    @GetMapping("/hello")
    public String getMessage() {
        log.info("Api hello called");
        return "Hello from version 4 on " + hostname;
    }

    @GetMapping("/conf")
    public String getConf() {
        log.info("Api conf called");
        return conf;
    }

    @GetMapping("/headers")
    public void printHeaders(@RequestHeader Map<String, String> headers) {
        headers.entrySet().forEach(stringStringEntry ->
                log.info(stringStringEntry.getKey() + ": " + stringStringEntry.getValue()));
    }

    @PostMapping(path="/add")
    public String addNewUser (@RequestParam String name, @RequestParam String email) {
        User n = new User();
        n.setName(name);
        n.setEmail(email);
        userRepository.save(n);
        return "Saved";
    }

    @GetMapping("/api/error")
    public void getError() {
        log.info("Api error was called");
        int game = random.nextInt(100);
        if (game <= 20)
            throw new RuntimeException("Error");
        throw new RuntimeException("Error");
    }

    @GetMapping(path="/all")
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
