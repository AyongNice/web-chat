package com.example.ayongchat.enitiy;


import lombok.Data;

import java.time.LocalDate;

/**
 * 朋友圈评论dto
 */

@Data
public class CommentCircle {
    /**
     * create table friend_circle_comment
     * (
     * id            varchar(50)                        not null comment '主键ID'
     * primary key,
     * circle_id     varchar(50)                        not null comment '朋友圈ID',
     * user_id       varchar(50)                        not null comment '评论者ID',
     * user_name     varchar(100)                       not null comment '评论者姓名',
     * commentMessage       text                               not null comment '评论内容',
     * created_at    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
     * receiver_id   varchar(50)                        null comment '接收者ID',
     * receiver_name varchar(100)                       null comment '接收者姓名'
     * )
     * comment '朋友圈评论表';
     */
    private String id;
    private String circleId;
    private String userId;
    private String userName;
    private String commentMessage;
    private LocalDate createdAt;
    private String receiverId;
    private String receiverName;
    private String[] ids;
}
