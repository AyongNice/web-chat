package com.example.ayongchat.enitiy;


import lombok.Data;

import java.util.List;


/**
 * @Author:ayong
 * @Description: 查询朋友圈点赞的实体类
 */
@Data
public class FindLikeDto {
    private String id;
    private String userId;
    private String avatar;
    private String circleId;
    private String createdAt;
    private String[] ids;

}
