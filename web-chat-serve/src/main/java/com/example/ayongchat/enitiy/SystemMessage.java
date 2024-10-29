package com.example.ayongchat.enitiy;


import lombok.Data;

@Data
public class SystemMessage {
    private String sendId;
    private String receiveId;
    private String remark;
}
