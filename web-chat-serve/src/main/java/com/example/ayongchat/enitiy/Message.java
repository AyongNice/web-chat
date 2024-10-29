package com.example.ayongchat.enitiy;


import lombok.Data;

@Data
public class Message {

    /**
     * id             bigint auto_increment
     * primary key,
     * sender_id      varchar(50)                         null,
     * receiver_id    varchar(50)                         null,
     * content_type   tinyint                              txt  img  audio  video file ,
     * content_Status tinyint                             null,
     * message        text                                null,
     * timestamp      timestamp default CURRENT_TIMESTAMP null,
     * message_type   tinyint   default 1                 null comment '消息类型 single 单聊 。group  群聊'
     */

    private Long id;
    private String openId;
    private String friendId;
    private String contentType;
    private String contentStatus;
    private String message;
    private String timestamp;
    private String messageType;



}
