package com.example.ayongchat.mapper;


import com.example.ayongchat.enitiy.RegisterDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegisterMapper {
    int insertRegister(RegisterDto registerDto);
}
