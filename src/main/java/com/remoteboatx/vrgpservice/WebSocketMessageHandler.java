package com.remoteboatx.vrgpservice;

import org.java_websocket.client.WebSocketClient;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

public class WebSocketMessageHandler extends TextWebSocketHandler {

    Map<WebSocketSession, BridgeWebSocket> bridgeWebSockets = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("new adapter connection");
        BridgeWebSocket moc = new BridgeWebSocket("ws://host.docker.internal:8081/ws");
        bridgeWebSockets.put(session, moc);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println("closed adapter connection");
        bridgeWebSockets.remove(session);
    }


    // Messages ADAPTER --> VRGP SERVICE 
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        if (!session.isOpen()) {
            System.out.println("trying to access closed adapter connection");
            return;
        }
        System.out.println("send message to moc");
        WebSocketClient moc = bridgeWebSockets.get(session).getClient();
        String payload = message.getPayload().toString();
        moc.send(payload);
    }
}
