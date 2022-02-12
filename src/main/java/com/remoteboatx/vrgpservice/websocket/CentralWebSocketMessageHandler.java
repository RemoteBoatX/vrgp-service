package main.java.com.remoteboatx.vrgpservice.websocket;

import java.net.URI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.remoteboatx.vrgpservice.websocket.WebSocketConnection;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.HashMap;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class CentralWebSocketMessageHandler extends TextWebSocketHandler{

    private AdapterWebSocketMessageHandler adapterHandler  = new AdapterWebSocketMessageHandler();
    private MocWebSocketMessageHandler     mocHandler      = new MocWebSocketMessageHandler();

    private final Map<WebSocketSession, SessionType> connectionTypes = new HashMap<>();
    
    public enum SessionType{
        MOC, ADAPTER
    }


    public void afterConnectionEstablished(WebSocketSession session, SessionType type) {

        connectionTypes.put(session,type);

        if(type == SessionType.ADAPTER){
            adapterHandler.afterConnectionEstablished(session);
        }
        else{
            mocHandler.afterConnectionEstablished(session);
        }
    }   

    @Override
    public void handleTextMessage(WebSocketSession session,TextMessage message){
        SessionType type = connectionTypes.get(session);

        final JsonNode jsonMessage;
        try {
            jsonMessage = new ObjectMapper().readTree(message.getPayload());
        } catch (JsonProcessingException e) {
            // TODO: Handle invalid JSON.
            e.printStackTrace();
            return;
        }

        jsonMessage.fieldNames().forEachRemaining((singleMessageKey) -> { 
            if(singleMessageKey == "connect"){
                String URL = jsonMessage.get("connect").asText();

                this.connectToMoc(URL);
            } 
        });
    }

    public void afterConnectionClosed(WebSocketSession session, SessionType type, CloseStatus status) {
        connectionTypes.remove(session);

        if(type == SessionType.ADAPTER){
            try{
                adapterHandler.afterConnectionClosed(session,status);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else{
            try{
                mocHandler.afterConnectionClosed(session,status);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    } 

    
    public void connectToMoc(String url){
        WebSocketConnection.MakeConnection(this, URI.create(url));
    }
    
}