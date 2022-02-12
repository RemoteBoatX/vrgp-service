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


public class AdapterWebSocketMessageHandler extends TextWebSocketHandler {

    protected static WebSocketSession adapterSession;


    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        adapterSession = session;
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
        adapterSession = null;
    }

}
