package com.remoteboatx.vrgpservice.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


abstract class WebSocketMessageHandler extends TextWebSocketHandler {

    protected static final List<WebSocketSession> connections =
            Collections.synchronizedList(new ArrayList<>()); //shared list for websocket connections

    public WebSocketMessageHandler() {

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        connections.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        connections.remove(session);
    }
}
