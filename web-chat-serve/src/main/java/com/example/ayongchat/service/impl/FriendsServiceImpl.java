package com.example.ayongchat.service.impl;


import cn.hutool.core.util.IdUtil;
import com.example.ayongchat.constant.Constant;
import com.example.ayongchat.enitiy.Friends;
import com.example.ayongchat.enitiy.GroupDto;
import com.example.ayongchat.mapper.FriendsMapper;
import com.example.ayongchat.service.FriendsService;
import com.example.loginsever.utlis.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class FriendsServiceImpl implements FriendsService {

    @Autowired
    private FriendsMapper friendsMapper;


    @Autowired
    JwtUtil jwtUtil;

    @Override
    public List<Friends> getFriendsList(String authorization) {
        String openId = jwtUtil.getID(authorization);


        return friendsMapper.getFriendsLists(openId);
    }


    /**
     * 根据条件搜索好友
     *
     * @param friendId
     * @param authorization
     * @return
     */
    public Friends searchFriends(String friendId, String authorization) {

        return friendsMapper.searchFriends(friendId, jwtUtil.getID(authorization));

    }

    /**
     * 查询 好友请求记录
     */
    public List<Friends> findAddRecords(String authorization) {
        return friendsMapper.findAddRecords(jwtUtil.getID(authorization));
    }

    /**
     * 处理好友请求
     */
    public void handleFriend(String sendId, String authorization, String handleStatus) {
        friendsMapper.handleFriend(sendId, jwtUtil.getID(authorization), handleStatus);
        if (Constant.agree.equals(handleStatus)) {
            //添加好友关系表
            friendsMapper.addFriends(jwtUtil.getID(authorization), sendId);
        }
    }

    /**
     * 修改备注
     */
    public void updateFriendNote(Friends friends, String authorization) {
        friendsMapper.updateFriendNote(friends);
    }

    /**
     * 删除好友
     *
     * @param friendId      删除的好友id
     * @param authorization 授权当前用户id
     */
    @Override
    public void deleteFriend(String friendId, String authorization) {
        friendsMapper.deleteFriend(friendId, jwtUtil.getID(authorization));
    }

    /**
     * 添加群聊
     */
    public String addGroup(List<String> list, String authorization) {
        list.add(jwtUtil.getID(authorization));
        //雪花id
        String id = IdUtil.getSnowflakeNextIdStr();
        GroupDto groupDto = new GroupDto();
        groupDto.setId(id);
        groupDto.setLeader(jwtUtil.getID(authorization));
        groupDto.setJoinDate(LocalDate.now());
        //list 将集合转换为字符串 ,分割


        groupDto.setList(String.join(",", list));
        friendsMapper.addGroup(groupDto);

        return id;
    }

    /**
     * 获取群聊信息
     */

    public GroupDto getGroupInfo(String groupId) {
        return friendsMapper.getGroupInfo(groupId);
    }


    /**
     * 获取群聊列表
     */
    public List<GroupDto> getGroupList(String authorization) {
        return friendsMapper.getGroupList(jwtUtil.getID(authorization));
    }

    /**
     * 根据群id 获取当前群成员信息
     */
    public List<Friends> getGroupMember(String groupId){
        return friendsMapper.getGroupMember(groupId);
    }


}
