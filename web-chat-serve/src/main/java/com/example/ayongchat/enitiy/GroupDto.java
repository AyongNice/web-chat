package com.example.ayongchat.enitiy;


import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Ayong
 * 群聊
 */
@Data
public class GroupDto {
    private String id;
    private String leader;
    private String name; // 群聊名称
    private LocalDate joinDate;
    private String list;
    private String username;

}
