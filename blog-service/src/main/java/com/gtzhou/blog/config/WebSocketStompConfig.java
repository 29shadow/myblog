package com.gtzhou.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/service/chat").setAllowedOrigins("*").withSockJS();
    }

    @Override
    //配置消息代理
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //广播式配置一个topic的消息代理
        registry.enableSimpleBroker("/topic");
        JdbcTemplate template = new JdbcTemplate();
    }
}
