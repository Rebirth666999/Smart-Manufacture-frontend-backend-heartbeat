package com.ruoyi.heart_beat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.ruoyi")
@EnableScheduling
public class HeartBeat1Application {
    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public AutowireCapableBeanFactory autowireCapableBeanFactory() {
        return applicationContext.getAutowireCapableBeanFactory();
    }
    public static void main(String[] args) {
        SpringApplication.run(HeartBeat1Application.class, args);
    }

}
