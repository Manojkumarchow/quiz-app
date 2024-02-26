package com.example.dormnestapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;

@SpringBootApplication
@EnableSpringHttpSession
//@EnableCaching
public class DormNestApp {

    public static void main(String[] args) {
        SpringApplication.run(DormNestApp.class, args);
    }

}
