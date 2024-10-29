package com.example.ayongchat.service;


import com.example.ayongchat.enitiy.Message;
import com.example.ayongchat.enitiy.RecordDto;
import com.example.ayongchat.enitiy.SystemMessage;
import com.github.pagehelper.PageInfo;


/**
 * 聊天记录保存
 */
public interface ChatService {


    /**
     * 聊天记录保存
     *
     * @param senderId
     * @param receiverId
     * @param message
     */
    public void saveMessage(Message message);

    /**
     * 获取消息记录
     *
     * @param friendId      好友id
     * @param authorization token 当前用户
     */
    public PageInfo<Message> getMessageList(RecordDto recordDto, String authorization);


    /**
     * 保存系统消息
     *
     * @param payload
     */
    void saveSystemMessage(SystemMessage payload);


    /**
     * 群聊天记录保存
     *
     * @param senderId
     * @param receiverId
     * @param message
     */
    void saveGroupMessage(Message payload);


    /**
     * 获取群消息记录
     *
     * @param friendId 群id
     */
    PageInfo<Message> getGroupRecords(RecordDto recordDto);
}
