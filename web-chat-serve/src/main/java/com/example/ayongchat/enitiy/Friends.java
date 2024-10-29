package com.example.ayongchat.enitiy;


import lombok.Data;

import java.io.Serializable;

/**
 * 好友列表
 */

@Data
public class Friends implements Serializable {
    /**
     * -- 表: friend_list
     * CREATE TABLE friend_list (
     * id INT AUTO_INCREMENT PRIMARY KEY,                -- 主键ID
     * openId VARCHAR(255) COMMENT '用户ID',             -- 用户ID
     * friendId VARCHAR(255) COMMENT '朋友ID',           -- 朋友ID
     * avatar VARCHAR(255) COMMENT '朋友头像',           -- 朋友头像
     * username VARCHAR(255) COMMENT '朋友用户名',       -- 朋友用户名
     * signature TEXT COMMENT '个性签名',                -- 个性签名
     * note TEXT COMMENT '备注',                         -- 备注
     * msgs JSON COMMENT '消息 (数组)',                  -- 消息 (数组)
     * li VARCHAR(255) COMMENT '额外信息',               -- 额外信息
     * INDEX (openId)                                    -- 为 openId 创建索引
     * );
     */
    private String openId;
    private String friendId;
    private String avatar;
    private String username;
    private String signature;
    private String note;
    private String msgs;
    private String li;
    private Integer status; // 0:代同意 1:已同意 2:已拒绝
}
