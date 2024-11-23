package com.example.ayongchat.service;


import com.example.ayongchat.enitiy.RegisterDto;

/**
 * 注册新用户
 */
public interface RegisterService {


    /**
     * 注册新用户
     * @param RegisterDto registerDto
     * @return
     */
    String register(RegisterDto registerDto);

}
