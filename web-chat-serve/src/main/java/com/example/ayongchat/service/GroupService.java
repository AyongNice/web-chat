package com.example.ayongchat.service;


import com.example.ayongchat.enitiy.Friends;
import com.example.ayongchat.enitiy.GroupDto;
import com.example.ayongchat.enitiy.RemoveGroupUserDto;

import java.util.List;

/**
 * @author AyongYang
 * 群聊
 */
public interface GroupService {


    /**
     * 删除群好友
     *
     * @param removeGroupUserDto 群好友信息
     * @param authorization      token
     * @param kopyList           群成员列表 由切面返回
     */
    void deleteGroupUser(RemoveGroupUserDto removeGroupUserDto, String authorization, List<String> kopyList);

    /**
     * 添加群好友
     *
     * @param removeGroupUserDto 群好友信息
     * @param authorization      token
     * @param kopyList           群成员列表 由切面返回
     */
    void addGroupUser(RemoveGroupUserDto removeGroupUserDto, String authorization, List<String> kopyList);
}
