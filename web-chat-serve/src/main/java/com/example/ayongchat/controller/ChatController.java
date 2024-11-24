package com.example.ayongchat.controller;


import com.example.ayongchat.constant.Constant;
import com.example.ayongchat.enitiy.Message;
import com.example.ayongchat.enitiy.SystemMessage;
import com.example.ayongchat.service.ChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    /**
     * 单聊
     * 接收消息
     *
     * @param message
     * @throws JsonProcessingException
     */
    @MessageMapping("/sendMessage")
    @SendTo("/broadcast")
    public void handleMessage(String message) throws JsonProcessingException {

        Message payload = objectMapper.readValue(message, Message.class);

        // 发送消息给指定的 friendId
        String destination = "/broadcast/" + payload.getFriendId() + "/queue/messages";

        //对象转换成json
        String jsonMessage = objectMapper.writeValueAsString(payload);

        messagingTemplate.convertAndSend(destination, jsonMessage);

        if (payload.getMessageType().equals(Constant.group)) {
            //Recording
            chatService.saveGroupMessage(payload);
        }

        if (payload.getMessageType().equals(Constant.friend)) {
            //Recording
            chatService.saveMessage(payload);
        }


    }


    /**
     * 添加好友
     *
     * @param message
     * @throws JsonProcessingException
     */

    @MessageMapping("/addFriend")
    @SendTo("/broadcast")
    public void handleGroupMessage(String systemMessage) throws JsonProcessingException {

        SystemMessage payload = objectMapper.readValue(systemMessage, SystemMessage.class);


        // 发送消息给指定的 friendId
        String destination = "/broadcast/" + payload.getReceiveId() + "/queue/addFriend";

        //对象转换成json
        String jsonMessage = objectMapper.writeValueAsString(payload);
        //Recording
        chatService.saveSystemMessage(payload);
        messagingTemplate.convertAndSend(destination, jsonMessage);

    }

    /**
     * 删除好友
     */


    /**
     * 删除群聊好友
     */
}
