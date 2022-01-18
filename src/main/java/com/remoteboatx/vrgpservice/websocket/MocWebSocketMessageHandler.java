package com.remoteboatx.vrgpservice.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.remoteboatx.vrgpservice.message.VrgpMessageType;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;


/**
 * WebSocket message handler for handling VRGP messages from the MOC
 */
public class MocWebSocketMessageHandler extends WebSocketMessageHandler {


    public MocWebSocketMessageHandler() {

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        super.afterConnectionEstablished(session);
        System.out.println("Moc connected");
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {

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
}
