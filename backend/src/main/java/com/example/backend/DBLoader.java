package com.example.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
public class DBLoader implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DBLoader.class);

    private final UserRepository userRepository;

    public DBLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("EXECUTING : Run method of Application Runner");
        User n = new User();
        n.setName("user");
        n.setEmail("user@email.com");
        userRepository.save(n);
        log.info("FINISHED : Run method of Application Runner");
    }
}
