package com.remoteboatx.vrgpservice.websocket;

import com.remoteboatx.vrgpservice.vrgp.message.VrgpMessage;
import com.remoteboatx.vrgpservice.vrgp.message.handler.ByeMessageHandler;
import com.remoteboatx.vrgpservice.vrgp.message.handler.LatencyMessageHandler;
import com.remoteboatx.vrgpservice.vrgp.message.handler.VrgpSingleMessageHandler;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MocWebSocketConnection extends TextWebSocketHandler implements MocWebSocket {

    private final List<VrgpSingleMessageHandler<?>> singleMessageHandlers = new ArrayList<>() {{
        add(new LatencyMessageHandler());
        add(new ByeMessageHandler());
    }};

    private WebSocketSession session;

    public MocWebSocketConnection(String url) {
        try {
            session = new StandardWebSocketClient().doHandshake(this, new WebSocketHttpHeaders(), URI.create(url))
                    .get(3, TimeUnit.MINUTES);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            //TODO handle if connection cannot be established
            e.printStackTrace();
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        for (VrgpSingleMessageHandler<?> singleMessageHandler : singleMessageHandlers) {
            singleMessageHandler.handleMessage(VrgpMessage.fromJson(message.getPayload()), this);
        }
    }

    @Override
    public void sendMessage(String message) {
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            // TODO: Handle exception.
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            session.close();
        } catch (IOException e) {
            // TODO: Handle exception.
            e.printStackTrace();
        }
    }
}