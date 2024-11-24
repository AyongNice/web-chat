package com.example.ayongchat.service;

import com.example.ayongchat.enitiy.Friends;
import com.example.ayongchat.enitiy.GroupDto;

import java.util.List;

/**
 * @author Ayong
 * 朋友管理
 */
public interface FriendsService {

    /**
     * 获取好友列表
     *
     * @param openId
     * @return
     */
    public List<Friends> getFriendsList(String openId);

    /**
     * 根据好友信息 查询好友信息
     * phone = #{friends}
     * OR u.username = #{friends}
     * OR u.name = #{friends}
     * OR u.email
     * 去查找符合条件的用户, 但是 不能查找自己, 也不能查找 friend_list 好友列表里 已存在的好友关系
     *
     * @param friends
     * @return Friends
     */
    Friends searchFriends(String friends, String authorization);


    /**
     * 获取好有请求记录
     *
     * @param authorization
     * @return
     */
    List<Friends> findAddRecords(String authorization);


    /**
     * 拒绝好友字段
     *
     * @param authorization
     * @param handleStatus  1 同意 2 拒绝
     */
    void handleFriend(String sendId, String authorization, String handleStatus);


    /**
     * 更新好友备注
     *
     * @param friends       要更新的好友信息id
     * @param authorization 授权当前用户id
     */
    void updateFriendNote(Friends friends, String authorization);


    /**
     * 删除好友
     *
     * @param friendId      删除的好友id
     * @param authorization 授权当前用户id
     */
    void deleteFriend(String friendId, String authorization);

    /**
     * 添加群聊
     */
    String addGroup(List<String> list, String authorization);


    /**
     * 通过 String groupId 获取群聊信息
     *
     * @param groupId
     * @return
     */
    GroupDto getGroupInfo(String groupId);


    /**
     * 获取群聊列表
     */
    List<GroupDto> getGroupList(String authorization);

    /**
     * 根据群id 获取当前群成员信息
     */
    List<Friends> getGroupMember(String groupId);
}
