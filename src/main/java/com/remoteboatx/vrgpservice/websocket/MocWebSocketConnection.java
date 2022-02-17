package com.remoteboatx.vrgpservice.websocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MocWebSocketConnection extends TextWebSocketHandler {

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
        // TODO: Implement.
        super.handleTextMessage(session, message);
    }

    public void sendMessage(String message) {
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            // TODO: Handle exception.
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            session.close();
        } catch (IOException e) {
            // TODO: Handle exception.
            e.printStackTrace();
        }
    }
}