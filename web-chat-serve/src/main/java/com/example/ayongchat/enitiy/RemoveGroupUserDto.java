package com.example.ayongchat.enitiy;

import lombok.Data;

import java.util.List;


@Data
public class RemoveGroupUserDto {
    private String groupId;
    private List<String> openIds;
}
