package com.example.ayongchat.controller;


import com.example.ayongchat.enitiy.Friends;
import com.example.ayongchat.enitiy.GroupDto;
import com.example.ayongchat.enitiy.RemoveGroupUserDto;
import com.example.ayongchat.service.FriendsService;
import com.example.ayongchat.service.GroupService;
import com.example.loginsever.utlis.Result;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;


    @Autowired
    private FriendsService friendsService;

    /**
     * 添加群聊
     */

    @PostMapping("/addGroup")
    public Result<String> addGroup(@RequestBody List<String> list, @RequestHeader("Authorization") String authorization) {
        return Result.success(friendsService.addGroup(list, authorization));
    }

    /**
     * 获取群信息
     */

    @GetMapping("/getGroupInfo")
    public Result<GroupDto> getGroupInfo(@Param("groupId") String groupId) {
        return Result.success(friendsService.getGroupInfo(groupId));
    }

    /**
     * 根据群id 获取当前群成员信息
     */
    @GetMapping("/getGroupMember")
    public Result<List<Friends>> getGroupMember(@Param("groupId") String groupId) {
        return Result.success(friendsService.getGroupMember(groupId));
    }


    /**
     * 删除群好友
     */
    @PostMapping("/removeFriends")
    public Result<String> deleteGroup(@RequestBody RemoveGroupUserDto removeGroupUserDto, @RequestHeader("Authorization") String authorization) {
        groupService.deleteGroupUser(removeGroupUserDto, authorization, null, null);
        return Result.success();
    }

    /**
     * 添加群好友
     */
    @PostMapping("/addGroupUser")
    public Result<String> addGroupUser(@RequestBody RemoveGroupUserDto removeGroupUserDto, @RequestHeader("Authorization") String authorization) {
        groupService.addGroupUser(removeGroupUserDto, authorization, null, null);
        return Result.success();
    }



}
