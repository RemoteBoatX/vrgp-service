package com.remoteboatx.vrgpservice.websocket;

import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * WebSocket client for connecting to WebSockets
 */
public class WebSocketClient {

    private WebSocketSession session;

    public WebSocketClient(TextWebSocketHandler messageHandler,
                           URI mocUri) throws ExecutionException, InterruptedException {


        //Creates a client websocket
        org.springframework.web.socket.client.WebSocketClient client = new StandardWebSocketClient();

        try {
            this.session = client.doHandshake(messageHandler, new WebSocketHttpHeaders(),
                    mocUri).get(3, TimeUnit.MINUTES);
        } catch (TimeoutException e) {
            //TODO handle if connection cannot be established
            e.printStackTrace();
        }
    }

    public WebSocketSession getSession() {
        return session;
    }
}
