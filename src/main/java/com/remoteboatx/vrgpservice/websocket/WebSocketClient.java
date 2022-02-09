package com.remoteboatx.vrgpservice.websocket;

import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
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
    private final org.springframework.web.socket.client.WebSocketClient client;
    private final TextWebSocketHandler messageHandler;
    private final URI uri;
    private final WebSocketHttpHeaders headers;

    public WebSocketClient(TextWebSocketHandler messageHandler, URI uri) {
        this.messageHandler = messageHandler;
        this.uri = uri;
        this.headers = new WebSocketHttpHeaders();

        //Creates a client websocket
        client = new StandardWebSocketClient();
    }

    public WebSocketClient(TextWebSocketHandler messageHandler, WebSocketHttpHeaders headers, URI uri) {
        this.messageHandler = messageHandler;
        this.uri = uri;
        this.headers = headers;

        //Creates a client websocket
        client = new StandardWebSocketClient();
    }


    /**
     * Starts a WebSocket handshake
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    //todo maybe change name??
    public void startHandshake() throws InterruptedException, ExecutionException, TimeoutException {

        //starts websocket handshake
        session = client.doHandshake(messageHandler, headers,
                uri).get(3, TimeUnit.MINUTES);

    }

    /**
     * Starts a WebSocket handshake with callbacks
     * @param successCallback success callback
     * @param failureCallback failure callback
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    public void startHandshake(SuccessCallback< ? super WebSocketSession> successCallback, FailureCallback failureCallback) throws InterruptedException, ExecutionException, TimeoutException {

        //starts websocket handshake
        ListenableFuture<WebSocketSession> handshake = client.doHandshake(messageHandler, headers , uri);
        handshake.addCallback(successCallback, failureCallback);
        session = handshake.get(3, TimeUnit.MINUTES);

    }


    public WebSocketSession getSession() {
        return session;
    }
}
