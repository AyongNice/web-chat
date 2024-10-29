package com.example.ayongchat.controller;


import com.example.ayongchat.constant.Constant;
import com.example.ayongchat.enitiy.Friends;
import com.example.ayongchat.enitiy.GroupDto;
import com.example.ayongchat.service.FriendsService;
import com.example.loginsever.utlis.Result;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/friends")
public class FriendsController {


    @Autowired
    private FriendsService friendsService;

    /**
     * 获取好友列表
     */
    @RequestMapping("/findByFriendList")
    public Result<List<Friends>> getFriendsList(@RequestHeader("Authorization") String authorization) {
        return Result.success(friendsService.getFriendsList(authorization));
    }

    /**
     * 获取群聊列表
     */
    @GetMapping("/groupList")
    public Result<List<GroupDto>> getGroupList(@RequestHeader("Authorization") String authorization) {
        return Result.success(friendsService.getGroupList(authorization));
    }

    /*
     * 根据
     * 搜索好友
     */
    @PostMapping("/searchEmail")
    public Result<Friends> searchFriends(@RequestBody Map<String, String> map, @RequestHeader("Authorization") String authorization) {
        return Result.success(friendsService.searchFriends(map.get("email"), authorization));
    }

    /**
     * 获取系统消息 好友请求记录
     */

    @GetMapping("/findAddRecords")
    public Result<List<Friends>> findAddRecords(@RequestHeader("Authorization") String authorization) {
        return Result.success(friendsService.findAddRecords(authorization));
    }


    /**
     * 处理 拒绝 好友请求
     */
    @PostMapping("/refuseAddRecordsImpl")
    public Result<String> refuseAddRecordsImpl(@RequestBody Map<String, String> map, @RequestHeader("Authorization") String authorization) {
        friendsService.handleFriend(map.get("openId"), authorization, Constant.refuse);
        return Result.success("处理成功");
    }

    /**
     * 处理 同意好友请求
     */
    @PostMapping("/consentAddRecords")
    public Result<String> consentAddRecords(@RequestBody Map<String, String> map, @RequestHeader("Authorization") String authorization) {
        friendsService.handleFriend(map.get("openId"), authorization, Constant.agree);
        return Result.success("处理成功");
    }

    /**
     * 修改备注
     */
    @PutMapping("/updateFriendNote")
    public Result<String> updateFriendNote(@RequestBody Friends friends, @RequestHeader("Authorization") String authorization) {
        friendsService.updateFriendNote(friends, authorization);
        return Result.success("修改成功");
    }

    /**
     * 删除好友
     */
    @PostMapping("/deleteFriend")
    public Result<String> deleteFriend(@RequestBody Map<String, String> map, @RequestHeader("Authorization") String authorization) {
        friendsService.deleteFriend(map.get("friendId"), authorization);
        return Result.success("删除成功");
    }


}
