package com.remoteboatx.vrgpservice.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;


public class VrgpWebsocketMessageHandler extends TextWebSocketHandler {

    protected static final Map<String,VrgpWebSocketSession> connections =
           new HashMap<>(); //shared list for websocket connections

    private final AdapterWebSocketMessageHandler adapterWebSocketMessageHandler =
            new AdapterWebSocketMessageHandler(this);

    private final MocWebSocketMessageHandler mocWebSocketMessageHandler = new MocWebSocketMessageHandler();

    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        //retrieves only an adapter connection
        connections.put(session.getId(), new VrgpWebSocketSession(VrgpWebSocketSession.SessionType.ADAPTER,
                session));
        adapterWebSocketMessageHandler.afterConnectionEstablished(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message){

        JsonNode jsonMessage;
        try {
            jsonMessage = new ObjectMapper().readTree(message.getPayload());

            VrgpWebSocketSession vrgpSession = connections.get(session.getId());

            switch (vrgpSession.getType()){
                case MOC: mocWebSocketMessageHandler.handleJsonMessage(session, jsonMessage);
                    break;
                case ADAPTER: adapterWebSocketMessageHandler.handleJsonMessage(session, jsonMessage);
                    break;
            }


        } catch (JsonProcessingException e) {
            //TODO handle
            e.printStackTrace();
            return;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
        connections.remove(session.getId());
    }


    public AdapterWebSocketMessageHandler getAdapterWebSocketMessageHandler() {
        return adapterWebSocketMessageHandler;
    }

    public MocWebSocketMessageHandler getMocWebSocketMessageHandler() {
        return mocWebSocketMessageHandler;
    }

    public void addSession(VrgpWebSocketSession session){
        if(!connections.containsKey(session.getSession().getId())){
            connections.put(session.getSession().getId(), session);
        }
    }
}
