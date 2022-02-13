package com.remoteboatx.vrgpservice.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


public class AdapterWebSocketMessageHandler extends TextWebSocketHandler {

    protected static WebSocketSession adapterSession;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        adapterSession = session;
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        adapterSession = null;
    }

}
