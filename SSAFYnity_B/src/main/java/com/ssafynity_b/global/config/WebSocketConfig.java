package com.ssafynity_b.global.config;

import com.ssafynity_b.domain.message.service.MessageService;
import com.ssafynity_b.domain.notification.service.NotificationService;
import com.ssafynity_b.global.handler.WebSocketHandler;
import com.ssafynity_b.global.interceptor.WebSocketHandshakeInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final MessageService messageService;
    private final NotificationService notificationService;

    @Bean
    public WebSocketHandler webSocketHandler() {
        return new WebSocketHandler(messageService, notificationService);
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(), "/ws")
                .addInterceptors(new WebSocketHandshakeInterceptor())
                .setAllowedOrigins("*");
    }
}
