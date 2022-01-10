package com.remoteboatx.vrgpservice.websocket;

import com.remoteboatx.vrgpservice.message.VrgpMessageType;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


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
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        connections.remove(session);
    }


    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

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

        JSONObject jsonMessage = (JSONObject) JSONValue.parse(message.getPayload());

        for(Object jsonMessageKey: jsonMessage.keySet()){

            String messageKey = (String) jsonMessageKey;  //message type
            JSONObject messageContent = (JSONObject) jsonMessage.get(messageKey); //message content

            VrgpMessageType.getByMessageKey(messageKey).getMessageHandler()
                    .handleMessage(session, messageContent);

        }
    }


    public void addSession(WebSocketSession session, ConnectionType connectionType){
        connections.add(session);
        connectionTypes.put(session, connectionType);
    }


    public enum ConnectionType{
        MOC(), ADAPTER();
    }



}
