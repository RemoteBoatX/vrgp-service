package com.remoteboatx.moc;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class WebSocketMessageHandler extends TextWebSocketHandler {

    List< BridgeWebSocket> webSocketSessions = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        
        BridgeWebSocket con = new BridgeWebSocket(session, "http://localhost:8081/");
        webSocketSessions.add(con);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        for(BridgeWebSocket item : webSocketSessions)
        {
            if(item.getSession() == session){
                webSocketSessions.remove(item);
            }
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
        WebSocketClient client = null;
        for(BridgeWebSocket item : webSocketSessions)
        {
            if(item.getSession() == session){
                client = item.getClient();
            }
        }
        
        String payload = message.getPayload().toString();
        client.send(payload);
    }
}
