package com.ruoyi.heart_beat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HeartBeat1Application {

    public static void main(String[] args) {
        SpringApplication.run(HeartBeat1Application.class, args);
    }

}
