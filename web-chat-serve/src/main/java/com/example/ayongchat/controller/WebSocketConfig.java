package com.example.ayongchat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 配置消息代理，用于广播消息给所有连接的客户端
        config.enableSimpleBroker("/broadcast");  // 广播消息的前缀
        config.setApplicationDestinationPrefixes("/app");  // 客户端发送消息的前缀
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册 STOMP 端点，并使用 SockJS 作为回退
        registry.addEndpoint("/room")
                .setAllowedOrigins("http://127.0.0.1:5500", "http://localhost:8080", "http://192.168.0.104:8080", "http://192.168.1.3:8080")
                .withSockJS();  // 允许跨域请求并使用 SockJS
    }

}
