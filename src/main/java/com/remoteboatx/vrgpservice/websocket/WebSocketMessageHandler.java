package com.remoteboatx.vrgpservice.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.remoteboatx.vrgpservice.message.VrgpMessageType;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WebSocketMessageHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> connections = new ArrayList<>();

    private final Map<WebSocketSession, ConnectionType> connectionTypes = new HashMap<>();


    public WebSocketMessageHandler() {

    }

    public void afterConnectionEstablished(WebSocketSession session, ConnectionType type){

        connectionTypes.put(session, type);
        connections.add(session);

        if(type.equals(ConnectionType.ADAPTER)){

            //After connection is established with the adapter
            System.out.println("Adapter connected");

        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        connections.remove(session);
    }


    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {

        switch (connectionTypes.get(session)){
            case MOC:
                handleMocMessage(session, message);
                break;
            case ADAPTER:
                handleAdapterMessage(session, message);
                break;
        }

    }


    private void handleAdapterMessage(WebSocketSession session, TextMessage message){

    }


    private void handleMocMessage(WebSocketSession session, TextMessage message){

        JsonNode jsonMessage;
        try {
            jsonMessage = new ObjectMapper().readTree(message.getPayload());

        } catch (JsonProcessingException e) {
            //TODO handle
            e.printStackTrace();
            return;
        }

        jsonMessage.fieldNames().forEachRemaining(messageKey -> {

            try {
                JsonNode messageContent = jsonMessage.get(messageKey); //message content

                VrgpMessageType.getByMessageKey(messageKey).getMessageHandler()
                        .handleMessage(session, messageContent);

            }catch (UnsupportedOperationException e){
                //TODO handle unsupported messages
                e.printStackTrace();
            }
        });
    }


    public void addSession(WebSocketSession session, ConnectionType connectionType){
        connections.add(session);
        connectionTypes.put(session, connectionType);
    }


    public enum ConnectionType{
        MOC(), ADAPTER()
    }
}
