package com.remoteboatx.vrgpservice;

import com.remoteboatx.vrgpservice.websocket.CentralWebSocketMessageHandler;
import com.remoteboatx.vrgpservice.websocket.VrgpWebSocketMessageHandlerWrapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {

        webSocketHandlerRegistry.addHandler(new VrgpWebSocketMessageHandlerWrapper(new CentralWebSocketMessageHandler(),
                        CentralWebSocketMessageHandler.SessionType.ADAPTER), "/service")
                // TODO: Set to actually allowed origins.
                .setAllowedOriginPatterns("*");

    }
}