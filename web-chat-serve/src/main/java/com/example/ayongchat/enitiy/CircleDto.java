package com.example.ayongchat.enitiy;


import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * 新增朋友圈实体
 *
 * @author AyongYang
 * @date 2024/10/21
 */

@Data
public class CircleDto {
    //朋友圈id
    private String id;
    private String userName;
    private String avatar;
    //用户id
    private String userId;
    //内容
    private String message;

    private List<String> imageList;
    //图片
    private String uploadList;
    //时间
    private LocalDate createdAt;

    private String bucketPath;

    public void setImageList(String imageList) {
        this.imageList = List.of(imageList.split(","));
    }
}
