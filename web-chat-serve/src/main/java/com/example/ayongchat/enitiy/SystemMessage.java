package com.example.ayongchat.enitiy;


import lombok.Data;


/**
 * 系统消息
 */
@Data
public class SystemMessage {
    private String sendId;
    private String receiveId;
    private String remark;
}
