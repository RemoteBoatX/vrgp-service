package com.remoteboatx.vrgpservice.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.remoteboatx.vrgpservice.VrgpState;
import com.remoteboatx.vrgpservice.message.AdapterMessageType;
import com.remoteboatx.vrgpservice.message.VrgpMessageType;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;



/**
 * WebSocket message handler for handling VRGP messages from the Adapter
 */
public class AdapterWebSocketMessageHandler extends TextWebSocketHandler {

    VrgpWebsocketMessageHandler handler;
    public AdapterWebSocketMessageHandler(VrgpWebsocketMessageHandler handler) {
        this.handler = handler;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {

        System.out.println("Adapter connected");
        VrgpState.getInstance().setAdapterSessionId(session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus){

    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        JsonNode jsonMessage;
        try {
            jsonMessage = new ObjectMapper().readTree(message.getPayload());
            handleJsonMessage(session, jsonMessage);


        } catch (JsonProcessingException e) {
            //TODO handle
            e.printStackTrace();
            return;
        }


    }

    public void handleJsonMessage(WebSocketSession session, JsonNode message){

        message.fieldNames().forEachRemaining(messageKey -> {

            try {
                JsonNode messageContent =  message.get(messageKey); //message content


                System.out.println(messageKey + ": " + messageContent);
                //TODO this is testing, change to a connect handler method


                //checks if its an adapter message
                if(AdapterMessageType.contains(messageKey)){

                    AdapterMessageType.getByMessageKey(messageKey).getMessageHandler()
                            .handleMessage(session, messageContent);
                }else{
                    VrgpMessageType.getByMessageKey(messageKey).getMessageHandler()
                            .handleMessage(session, messageContent);
                }


            }catch (UnsupportedOperationException e){
                //TODO handle unsupported messages
                e.printStackTrace();
            }
        });
    }
}
