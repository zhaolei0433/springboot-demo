package com.ipanel.web.app.cv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class LiveClassroomApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiveClassroomApplication.class, args);
    }
}
