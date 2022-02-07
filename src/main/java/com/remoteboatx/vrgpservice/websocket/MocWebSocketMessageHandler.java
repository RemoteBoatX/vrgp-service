package com.remoteboatx.vrgpservice.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.remoteboatx.vrgpservice.message.VrgpMessageType;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


/**
 * WebSocket message handler for handling VRGP messages from the MOC
 */
public class MocWebSocketMessageHandler extends TextWebSocketHandler {

    public MocWebSocketMessageHandler() {

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        System.out.println("Moc connected");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)  {

    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        JsonNode jsonMessage;
        try {
            jsonMessage = new ObjectMapper().readTree(message.getPayload());

        } catch (JsonProcessingException e) {
            //TODO handle
            e.printStackTrace();
            return;
        }

        handleJsonMessage(session, jsonMessage);
    }

    public void handleJsonMessage(WebSocketSession session, JsonNode message){

        message.fieldNames().forEachRemaining(messageKey -> {

            try {
                JsonNode messageContent =  message.get(messageKey); //message content

                VrgpMessageType.getByMessageKey(messageKey).getMessageHandler()
                        .handleMessage(session, messageContent);

            }catch (UnsupportedOperationException e){
                //TODO handle unsupported messages
                e.printStackTrace();
            }
        });
    }

}
