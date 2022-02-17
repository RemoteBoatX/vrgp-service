package com.remoteboatx.vrgpservice.websocket;

import com.remoteboatx.vrgpservice.adapter.message.ConnectMessage;
import com.remoteboatx.vrgpservice.adapter.message.handler.ConnectMessageHandler;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

public class AdapterWebSocketMessageHandler extends TextWebSocketHandler {

    protected static WebSocketSession adapterSession;

    private static AdapterWebSocketMessageHandler instance;

    private final List<ConnectMessageHandler> connectMessageHandlers = new ArrayList<>();

    private AdapterWebSocketMessageHandler() {
    }

    public static AdapterWebSocketMessageHandler getInstance() {
        if (instance == null) {
            instance = new AdapterWebSocketMessageHandler();
        }
        return instance;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        adapterSession = session;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        adapterSession = null;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // TODO: Implement.
        super.handleTextMessage(session, message);
    }

    public void registerConnectMessageHandler(ConnectMessageHandler connectMessageHandler) {
        connectMessageHandlers.add(connectMessageHandler);
    }

    private void notifyConnectMessageHandlers(ConnectMessage connectMessage) {
        connectMessageHandlers.forEach(connectMessageHandler -> connectMessageHandler.handleMessage(connectMessage));
    }
}
