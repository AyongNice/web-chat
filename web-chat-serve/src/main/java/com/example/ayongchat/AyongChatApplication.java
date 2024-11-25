package com.example.ayongchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * 关注微信公众号: 阿勇学前端
 */

@ComponentScan(basePackages = {"com.example.loginsever","com.example.ayongchat"})
@SpringBootApplication
public class AyongChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(AyongChatApplication.class, args);
    }

}
