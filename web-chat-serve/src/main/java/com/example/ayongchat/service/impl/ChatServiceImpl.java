package com.example.ayongchat.service.impl;


import cn.hutool.core.util.IdUtil;
import com.example.ayongchat.enitiy.Message;
import com.example.ayongchat.enitiy.RecordDto;
import com.example.ayongchat.enitiy.SystemMessage;
import com.example.ayongchat.mapper.ChatMessageMapper;
import com.example.ayongchat.service.ChatService;
import com.example.loginsever.utlis.JwtUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 聊天记录服务实现类
 */

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatMessageMapper chatMessageRepository;

    @Autowired
    JwtUtil jwtUtil;


    /**
     * 存储消息
     *
     * @param message
     */
    public void saveMessage(Message message) {
        message.setId(Long.valueOf(IdUtil.getSnowflakeNextIdStr()));
        chatMessageRepository.insertMessage(message);
    }


    /**
     * 获取当前好友聊天记录
     *
     * @param recordDto
     * @param authorization token 当前用户
     * @return
     */
    public PageInfo<Message> getMessageList(RecordDto recordDto, String authorization) {

        // 获取总记录数
        Integer totalCount = chatMessageRepository.countMessages(recordDto.getFriendId(), jwtUtil.getID(authorization));

        // 计算总页数
        int totalPages = (int) Math.ceil((double) totalCount / recordDto.getSize());

        // 确定要查询的页码，如果用户请求的页码超出总页数，则从最后一页开始
        // 确定从最后一页往前倒的实际页码
        int actualPage = totalPages - (recordDto.getPage() - 1);
        if (actualPage < 1) {
            actualPage = 1; // 防止页码越界
        }

        PageHelper.startPage(actualPage, recordDto.getSize());
        List<Message> list = chatMessageRepository.getMessageList(recordDto.getFriendId(), jwtUtil.getID(authorization));

        return new PageInfo<>(list);

    }

    /**
     * 获取群聊记录
     */
    public PageInfo<Message> getGroupRecords(RecordDto recordDto) {


        // 获取总记录数
        Integer totalCount = chatMessageRepository.countGroupMessages(recordDto.getFriendId());


        // 计算总页数
        int totalPages = (int) Math.ceil((double) totalCount / recordDto.getSize());

        // 确定要查询的页码，如果用户请求的页码超出总页数，则从最后一页开始
        // 确定从最后一页往前倒的实际页码
        int actualPage = totalPages - (recordDto.getPage() - 1);
        if (actualPage < 1) {
            actualPage = 1; // 防止页码越界
        }

        PageHelper.startPage(actualPage, recordDto.getSize());
        List<Message> list = chatMessageRepository.getGroupMessageList(recordDto.getFriendId());

        return new PageInfo<>(list);
    }

    /**
     * 保存系统消息
     */

    public void saveSystemMessage(SystemMessage payload) {
        List<SystemMessage> list = chatMessageRepository.getSystemMessageList(payload);
        // 如果存在，则不保存
        if (list.size() > 0) {
            return;
        }
        chatMessageRepository.insertSystemMessage(payload);
    }

    /**
     * 保存群聊消息
     */
    public void saveGroupMessage(Message message) {
        message.setId(Long.valueOf(IdUtil.getSnowflakeNextIdStr()));
        chatMessageRepository.insertGroupMessage(message);

    }


}
