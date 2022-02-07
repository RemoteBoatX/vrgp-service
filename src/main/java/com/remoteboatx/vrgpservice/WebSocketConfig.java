package com.remoteboatx.vrgpservice;

import com.remoteboatx.vrgpservice.websocket.VrgpWebsocketMessageHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {

        webSocketHandlerRegistry.addHandler(new VrgpWebsocketMessageHandler(), "/vessel")
                // TODO: Set to actually allowed origins.
                .setAllowedOriginPatterns("*");

    }
}