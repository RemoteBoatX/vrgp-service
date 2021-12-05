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
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);

        BridgeWebSocket con = new BridgeWebSocket("ws://host.docker.internal:8081/ws");
        bridgeWebSockets.put(session, con);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        bridgeWebSockets.remove(session);
    }


    // Messages ADAPTER --> VRGP SERVICE 
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
        WebSocketClient client = bridgeWebSockets.get(session).getClient();

        String payload = message.getPayload().toString();
        client.send(payload);
    }
}
