package com.example.ayongchat.enitiy;


import lombok.Data;

import java.time.LocalDate;

/**
 * 朋友圈点赞实体类
 *
 * @author AyongYang
 * @date 2024/10/22
 */
@Data
public class LikeDto {
    private String id;
    private String userId;
    private String circleId;
    private LocalDate createdAt;
    private String avatar;

}
