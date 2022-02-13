package com.remoteboatx.vrgpservice.websocket;

import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * WebSocket client for connecting to WebSockets
 */
public class WebSocketConnection implements WebSocketConnectionInterface {

    public static void MakeConnection(TextWebSocketHandler messageHandler, URI mocUri) {

        //Creates a client websocket
        org.springframework.web.socket.client.WebSocketClient client = new StandardWebSocketClient();

        try {
            client.doHandshake(messageHandler, new WebSocketHttpHeaders(), mocUri).get(3, TimeUnit.MINUTES);
        } catch (ExecutionException e) {
            //TODO handle if connection cannot be established
            e.printStackTrace();
        } catch (InterruptedException e) {
            //TODO handle if connection cannot be established
            e.printStackTrace();
        } catch (TimeoutException e) {
            //TODO handle if connection cannot be established
            e.printStackTrace();
        }


    }
}
