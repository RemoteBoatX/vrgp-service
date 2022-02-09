package com.remoteboatx.vrgpservice.message.adapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.remoteboatx.vrgpservice.VrgpState;
import com.remoteboatx.vrgpservice.message.VrgpMessageHandler;
import com.remoteboatx.vrgpservice.websocket.VrgpWebSocketSession;
import com.remoteboatx.vrgpservice.websocket.VrgpWebsocketMessageHandler;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class ConnectMessageHandler implements VrgpMessageHandler {

    @Override
    public void handleMessage(WebSocketSession vesselSession, Object message) {
        if(message instanceof JsonNode) {

            String url = ((JsonNode) message).get("url").asText();

            //get vrgp websocket handler
            VrgpWebsocketMessageHandler vrgpHandler = VrgpState.getInstance().getVrgpWebsocketMessageHandler();
            try {
                StatusCode statusCode = vrgpHandler.createWebSocketSession(url, VrgpWebSocketSession.SessionType.MOC);
                //CREATE NEW REPLY MESSAGE WITH STATUS, URL
                //TODO Replace with message object
                vesselSession.sendMessage(new TextMessage(String.format("{\"status\":{" +
                        "\"url\": %s," +
                        "\"status\": %d" +
                        "}" +
                                "}", url, statusCode.ordinal())));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
