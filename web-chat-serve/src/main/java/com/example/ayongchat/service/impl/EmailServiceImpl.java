package com.example.ayongchat.service.impl;

import com.example.ayongchat.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@ConfigurationProperties(prefix = "mail")
public class EmailServiceImpl implements EmailService {
    private String username;
    @Autowired
    private JavaMailSender javaMailSender;
    public void sendSimpleMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom(username);
        javaMailSender.send(message);
    }
}
