package com.example.ayongchat.enitiy;


import lombok.Data;


/**
 * @author Ayong
 * 注册请求参数实体类
 */
@Data
public class RegisterDto {

    private String id;

    // 邮箱
    private String email;
    // 密码
    private String passWord;
    // 验证码
    private String randomNum;
    // 昵称
    private String userName;
    // 头像
    private String avatar;


}
