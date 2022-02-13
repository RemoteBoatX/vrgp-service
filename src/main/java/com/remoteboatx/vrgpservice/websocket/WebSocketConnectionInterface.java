package com.remoteboatx.vrgpservice.websocket;

import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;

public interface WebSocketConnectionInterface {

    /**
     * Establish connection
     *
     * @param messageHandler handler for the connection
     * @param mocUri         URL to connect to
     */
    public static void MakeConnection(TextWebSocketHandler messageHandler, URI mocUri) {
    }

}
