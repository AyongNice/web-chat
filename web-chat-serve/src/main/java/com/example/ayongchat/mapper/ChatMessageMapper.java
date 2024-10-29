package com.example.ayongchat.mapper;


import com.example.ayongchat.enitiy.Message;
import com.example.ayongchat.enitiy.SystemMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMessageMapper {


    /**
     * 保存消息
     */
    void insertMessage(Message message);

    /**
     * 获取聊天记录
     *
     * @param friendId
     * @param openId
     * @return
     */
    List<Message> getMessageList(String friendId, String openId);

    Integer countMessages(String friendId, String openId);


    /**
     * 保存系统消息
     *
     * @param payload
     */
    void insertSystemMessage(SystemMessage payload);

    /**
     * 获取系统消息
     */
    List<SystemMessage> getSystemMessageList(SystemMessage payload);


    /**
     * 保存群聊消息
     */
    void insertGroupMessage(Message message);

    Integer countGroupMessages(String groupId);

    List<Message> getGroupMessageList(String groupId);
}
