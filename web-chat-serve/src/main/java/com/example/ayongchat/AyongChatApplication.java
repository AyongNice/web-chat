package com.example.ayongchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;



@ComponentScan(basePackages = {"com.example.loginsever","com.example.ayongchat"})
@SpringBootApplication
public class AyongChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(AyongChatApplication.class, args);
    }

}
