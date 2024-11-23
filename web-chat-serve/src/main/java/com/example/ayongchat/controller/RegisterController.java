package com.example.ayongchat.controller;


import com.example.ayongchat.enitiy.RegisterDto;
import com.example.ayongchat.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ayong
 * 注册账号
 */

@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterDto registerDto) {

        return registerService.register(registerDto);
    }

}
