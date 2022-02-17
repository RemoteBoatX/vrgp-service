package com.remoteboatx.vrgpservice.websocket;

import com.remoteboatx.vrgpservice.adapter.message.AdapterMessage;
import com.remoteboatx.vrgpservice.adapter.message.ByeMessage;
import com.remoteboatx.vrgpservice.adapter.message.ConnectMessage;
import com.remoteboatx.vrgpservice.adapter.message.handler.AdapterMessageHandler;
import com.remoteboatx.vrgpservice.util.JsonUtil;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

public class AdapterWebSocketMessageHandler extends TextWebSocketHandler {

    protected static WebSocketSession adapterSession;

    private static AdapterWebSocketMessageHandler instance;

    private final MocRepository mocs = new MocRepository();

    private final List<AdapterMessageHandler<ConnectMessage>> connectMessageHandlers = new ArrayList<>();

    private final List<AdapterMessageHandler<ByeMessage>> byeMessageHandlers = new ArrayList<>();

    private AdapterWebSocketMessageHandler() {
        registerConnectMessageHandler(connectMessage -> mocs.connectToMoc(connectMessage.getUrl()));
        registerByeMessageHandler(byeMessage -> mocs.disconnectFromMoc(byeMessage.getUrl()));
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
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        // TODO: Implement.
        final AdapterMessage adapterMessage = JsonUtil.fromJson(message.getPayload(), AdapterMessage.class);
        if (adapterMessage.getConnect() != null) {
            notifyConnectMessageHandlers(adapterMessage.getConnect());
        }
        if (adapterMessage.getBye() != null) {
            notifyByeMessageHandlers(adapterMessage.getBye());
        }
    }

    // Connect

    public void registerConnectMessageHandler(AdapterMessageHandler<ConnectMessage> connectMessageHandler) {
        connectMessageHandlers.add(connectMessageHandler);
    }

    private void notifyConnectMessageHandlers(ConnectMessage connectMessage) {
        connectMessageHandlers.forEach(connectMessageHandler -> connectMessageHandler.handleMessage(connectMessage));
    }

    // Bye

    public void registerByeMessageHandler(AdapterMessageHandler<ByeMessage> byeMessageHandler) {
        byeMessageHandlers.add(byeMessageHandler);
    }

    private void notifyByeMessageHandlers(ByeMessage byeMessage) {
        byeMessageHandlers.forEach(byeMessageHandler -> byeMessageHandler.handleMessage(byeMessage));
    }
}
