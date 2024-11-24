package com.example.ayongchat.controller;

import com.example.ayongchat.service.EmailService;
import com.example.loginsever.utlis.Result;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;


    @GetMapping("/email")
    public Result<String> sendEmail(@Param("to") String to) {

        emailService.sendSimpleMail(to);
        return Result.success(null, "发送成功");
    }
}
