package com.example.ayongchat.service.impl;

import com.example.ayongchat.constant.Constant;
import com.example.ayongchat.service.EmailService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;


@Service
@Data
@ConfigurationProperties(prefix = "mail")
public class EmailServiceImpl implements EmailService {
    private String username ;
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private StringRedisTemplate strRedisT;

    public String sendSimpleMail(String to) {

        int randomNum = ThreadLocalRandom.current().nextInt(Constant.max, Constant.min + 1);
        String random = this.strRedisT.opsForValue().get("email:" + to);
        if (random != null && random.equals(String.valueOf(randomNum))) {
            return "验证码已发送，请查收";
        }
        this.strRedisT.opsForValue().set("email:" + to, String.valueOf(randomNum), 60, TimeUnit.SECONDS);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("验证码");
        message.setText(String.valueOf(randomNum));
        message.setFrom(username);
        javaMailSender.send(message);
        return "验证码已发送，请查收";
    }
}
