package com.haishan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

@SpringBootApplication
public class UserApplicationController {

    public static void main(String[] args){
        SpringApplication.run(UserApplicationController.class, args);
    }
}
