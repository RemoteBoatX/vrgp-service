package com.remoteboatx.vrgpservice.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.remoteboatx.vrgpservice.message.adapter.StatusCode;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;


public class VrgpWebsocketMessageHandler extends TextWebSocketHandler {

    protected static final Map<String,VrgpWebSocketSession> connections =
           new HashMap<>(); //shared list for websocket connections

    private final AdapterWebSocketMessageHandler adapterWebSocketMessageHandler =
            new AdapterWebSocketMessageHandler(this);

    private final MocWebSocketMessageHandler mocWebSocketMessageHandler = new MocWebSocketMessageHandler(this);

    @Override
    public void afterConnectionEstablished(WebSocketSession session){

        System.out.println("established session: " + session.getId());

        //gets the session type from the websocket http headers
        if(session.getHandshakeHeaders().containsKey("type")){

            String sessionType = session.getHandshakeHeaders().get("type").get(0);

            System.out.println("session type: " + sessionType);

            switch (VrgpWebSocketSession.SessionType.valueOf(sessionType)) {

                case MOC: mocWebSocketMessageHandler.afterConnectionEstablished(session);
                    break;
                case ADAPTER:adapterWebSocketMessageHandler.afterConnectionEstablished(session);
                    connections.put(session.getId(), new VrgpWebSocketSession(VrgpWebSocketSession.SessionType.ADAPTER,
                            session, session.getUri()));
                    break;
            }
        }else{
            System.out.println("Unknown session type");
        }
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

        VrgpWebSocketSession vrgpSession = connections.get(session.getId());

        switch (vrgpSession.getType()){
            case MOC: mocWebSocketMessageHandler.afterConnectionClosed(session, status);
                break;
            case ADAPTER: adapterWebSocketMessageHandler.afterConnectionClosed(session, status);
                break;
        }

        connections.remove(session.getId());

    }


    public AdapterWebSocketMessageHandler getAdapterWebSocketMessageHandler() {
        return adapterWebSocketMessageHandler;
    }

    public MocWebSocketMessageHandler getMocWebSocketMessageHandler() {
        return mocWebSocketMessageHandler;
    }

    public VrgpWebSocketSession getSession(String id){
        if(connections.containsKey(id)){
            return connections.get(id);
        }
        return null;
    }

    /**
     * Creates a webSocket session
     * @param url target server url
     * @param type type of connection
     * @return TODO
     */
    public StatusCode createWebSocketSession(String url, VrgpWebSocketSession.SessionType type) {

        WebSocketClient client = null;

        try {

            WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
            headers.add("type", type.name());
            client = new WebSocketClient(this,headers, URI.create(url));

            client.startHandshake(session -> {
                //success callback
                connections.put(session.getId(), new VrgpWebSocketSession(type, session, URI.create(url)));
                System.out.println("success callback");

            }, (session) ->{
                //failure callback
                System.out.println("Handshake with " + url + " failed");
            });

        } catch (ExecutionException | InterruptedException e) {
            return StatusCode.ERROR;
        } catch (TimeoutException timeoutException) {
            return StatusCode.TIMEOUT;
        }
//        addSession(new VrgpWebSocketSession(type, client.getSession()));
        System.out.println("Created session: " + client.getSession().getId());
        return StatusCode.SUCCESS;
    }
}
