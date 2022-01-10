package com.remoteboatx.vrgpservice.websocket;

import org.springframework.web.socket.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.net.URI;
import java.util.concurrent.ExecutionException;

/**
 * Wrapper for {@link WebSocketMessageHandler}s to forward method calls from a
 * {@link WebSocketHandler} and creates a websocket connection to a server.
 */
public class ClientWebSocketMessageHandlerWrapper implements WebSocketHandler {

    private final WebSocketMessageHandler messageHandler;

    private final WebSocketMessageHandler.ConnectionType connectionType;

    /**
     * Constructs a {@link WebSocketHandler} that uses a {@link WebSocketMessageHandler} to
     * handle message by connections of a specific
     * {@link WebSocketMessageHandler.ConnectionType ConnectionType} and creates a websocket connection to the
     * specified server {@link URI uri}
     *
     * @param messageHandler the real message handler.
     * @param connectionType the connection type to be handled by this {@link WebSocketHandler}.
     * @param uri the server uri for connecting to a websocket
     */
    public ClientWebSocketMessageHandlerWrapper(WebSocketMessageHandler messageHandler,
                                                WebSocketMessageHandler.ConnectionType connectionType,
                                                URI uri) throws ExecutionException, InterruptedException {

        this.messageHandler = messageHandler;
        this.connectionType = connectionType;


        //Creates a client websocket
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketSession clientSession = client.doHandshake(messageHandler, new WebSocketHttpHeaders(),
                uri).get();
        messageHandler.addSession(clientSession, connectionType);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // Pass the connection type to the message handler.
        messageHandler.afterConnectionEstablished(session, connectionType);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        messageHandler.handleMessage(session, message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        messageHandler.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        messageHandler.afterConnectionClosed(session, closeStatus);
    }

    @Override
    public boolean supportsPartialMessages() {
        return messageHandler.supportsPartialMessages();
    }
}
