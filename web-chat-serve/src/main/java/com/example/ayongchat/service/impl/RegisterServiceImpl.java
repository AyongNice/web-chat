package com.example.ayongchat.service.impl;

import cn.hutool.core.util.IdUtil;
import com.example.ayongchat.enitiy.RegisterDto;
import com.example.ayongchat.mapper.RegisterMapper;
import com.example.ayongchat.service.RegisterService;
import com.example.loginsever.utlis.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class RegisterServiceImpl implements RegisterService {


    @Autowired
    private StringRedisTemplate strRedisT;

    @Autowired
    private RegisterMapper registerMapper;


    /**
     * 注册用户
     *
     * @param registerDto
     * @return
     */
    public String register(RegisterDto registerDto) {

        String random = this.strRedisT.opsForValue().get("email:" + registerDto.getEmail());
        if (random != null && random.equals(registerDto.getRandomNum())) {
            registerDto.setPassWord(PasswordUtil.encryptPassword(registerDto.getPassWord()));
            registerDto.setId(IdUtil.getSnowflakeNextIdStr());
            registerMapper.insertRegister(registerDto);
        }
        throw new RuntimeException("验证码不对");
    }
}
