package com.example.ayongchat.enitiy;

import lombok.Data;

import java.util.List;


/**
 * @author Ayong
 * @date 2024/11/13 - 14:07
 * @Description: 删除群成员
 */
@Data
public class RemoveGroupUserDto {
    private String groupId;
    private List<String> openIds;
}
