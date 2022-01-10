package com.remoteboatx.vrgpservice;

import com.remoteboatx.vrgpservice.websocket.WebSocketMessageHandler;
import com.remoteboatx.vrgpservice.websocket.WebSocketMessageHandlerWrapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.util.concurrent.ExecutionException;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {

        webSocketHandlerRegistry.addHandler(new WebSocketMessageHandlerWrapper(new WebSocketMessageHandler(),
                WebSocketMessageHandler.ConnectionType.ADAPTER), "/adapter")
                // TODO: Set to actually allowed origins.
                .setAllowedOriginPatterns("*");

    }
}