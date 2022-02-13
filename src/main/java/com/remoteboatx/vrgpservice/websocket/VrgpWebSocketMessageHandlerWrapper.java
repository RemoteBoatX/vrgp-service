package main.java.com.remoteboatx.vrgpservice.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.lang.InterruptedException;
import java.lang.Exception;


public class VrgpWebSocketMessageHandlerWrapper extends TextWebSocketHandler{
    CentralWebSocketMessageHandler                  handler; 
    CentralWebSocketMessageHandler.SessionType      type;

    public VrgpWebSocketMessageHandlerWrapper(CentralWebSocketMessageHandler messageHandler,CentralWebSocketMessageHandler.SessionType sessionType) {

        this.handler = messageHandler;
        this.type = sessionType;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // Pass the connection type to the message handler.
       handler.afterConnectionEstablished(session, type);
       System.out.println("<-UWU->");
    }

    @Override
    public void handleTextMessage(WebSocketSession session,TextMessage message){
        handler.handleTextMessage(session, message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session,CloseStatus status){
        try{
            handler.afterConnectionClosed(session,status);
        } catch(Exception e){
            e.printStackTrace();
        }
    }   
}
