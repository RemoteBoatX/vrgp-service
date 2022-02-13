package main.java.com.remoteboatx.vrgpservice.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.http.WebSocket;
import java.util.HashMap;
import java.util.Map;

import java.io.IOException;
import java.lang.Exception;


public class MocWebSocketMessageHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> MocConnections = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        MocConnections.put(session.getId(),session);
        System.out.println("Connection made");
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
        MocConnections.remove(session.getId());
    }

    public void sendMessage(WebSocketSession session, TextMessage message){
        try{
            session.sendMessage(message);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}