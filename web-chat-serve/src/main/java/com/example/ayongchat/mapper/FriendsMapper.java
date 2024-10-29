package com.example.ayongchat.mapper;


import com.example.ayongchat.enitiy.Friends;
import com.example.ayongchat.enitiy.GroupDto;
import com.example.ayongchat.enitiy.Message;
import com.example.ayongchat.enitiy.RemoveGroupUserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FriendsMapper {


    /**
     * 获取好友列表
     *
     * @param openId
     * @return
     */
    public List<Friends> getFriendsLists(String openId);

    /**
     * 根据好友条件查询好友信息
     *
     * @param friendId
     * @return
     */

    Friends searchFriends(String friends, String id);

    /**
     * 查询 好友请求记录
     */
    List<Friends> findAddRecords(String receiveId);


    /**
     * 处理好友请求
     */
    void handleFriend(String sendId, String receiveId, String handleStatus);


    /**
     * 添加好友关系表
     *
     * @param openId
     * @param friendId
     */
    void addFriends(String openId, String friendId);

    void updateFriendNote(Friends friends);

    void deleteFriend(String friendId, String openId);


    /**
     * 添加群聊
     *
     * @param list
     */
    void addGroup(GroupDto groupDto);


    /**
     * 获取群聊基本信息
     *
     * @param groupId
     * @return
     */
    GroupDto getGroupInfo(String groupId);


    /**
     * 获取群聊列表
     *
     * @param id
     * @return
     */
    List<GroupDto> getGroupList(String openId);


    /**
     * 根据群id 获取当前群成员信息
     */
    List<Friends> getGroupMember(String groupId);


    /**
     * 修改群成员
     *
     * @param GroupDto
     */

    void updataGroupFriend(GroupDto groupDto);
}
