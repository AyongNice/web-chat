package com.example.ayongchat.service.impl;


import com.example.ayongchat.aop.CheckGroupPermission;
import com.example.ayongchat.enitiy.GroupDto;
import com.example.ayongchat.enitiy.RemoveGroupUserDto;
import com.example.ayongchat.mapper.FriendsMapper;
import com.example.ayongchat.service.GroupService;
import com.example.loginsever.utlis.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private FriendsMapper friendsMapper;

    @Autowired
    JwtUtil jwtUtil;


    /**
     * 删除群好友
     *
     * @param removeGroupUserDto 群好友信息
     * @param authorization      token
     * @param kopyList           群好友列表 由切面返回 用于处理
     *
     */
    @Override
    @CheckGroupPermission
    public void deleteGroupUser(RemoveGroupUserDto removeGroupUserDto, String authorization,  List<String> kopyList) {

        if (kopyList == null) return;

        List<String> openIdsToRemove = removeGroupUserDto.getOpenIds();
        kopyList.removeIf(openIdsToRemove::contains);

        GroupDto groupDto = new GroupDto();
        groupDto.setId(removeGroupUserDto.getGroupId());
        groupDto.setList(String.join(",", kopyList));

        friendsMapper.updataGroupFriend(groupDto);

    }

    /**
     * 添加群好友
     *
     * @param removeGroupUserDto 群好友信息
     * @param authorization      token
     * @param kopyList          群信息 由切面返回
     */
    @CheckGroupPermission
    public void addGroupUser(RemoveGroupUserDto removeGroupUserDto, String authorization, List<String> kopyList) {

        if ( kopyList == null) return;

        kopyList.addAll(removeGroupUserDto.getOpenIds());

        GroupDto groupDto = new GroupDto();
        groupDto.setId(removeGroupUserDto.getGroupId());
        groupDto.setList(String.join(",", kopyList));

        friendsMapper.updataGroupFriend(groupDto);
    }


}
