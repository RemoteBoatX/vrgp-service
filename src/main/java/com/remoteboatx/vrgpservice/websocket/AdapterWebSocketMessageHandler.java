package com.remoteboatx.vrgpservice.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.concurrent.ExecutionException;


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
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

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
                if(messageKey.equals("connect")){

                    //connect to moc
                    try {
                        //TODO change to a dynamic way of retrieving the moc URI
                        WebSocketClient moc =  new WebSocketClient(handler.getMocWebSocketMessageHandler(),
                                URI.create("ws://host.docker.internal:8080/vessel"));
                        handler.addSession(new VrgpWebSocketSession(VrgpWebSocketSession.SessionType.MOC,
                                moc.getSession()));

                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
//                VrgpMessageType.getByMessageKey(messageKey).getMessageHandler()
//                        .handleMessage(session, messageContent);

            }catch (UnsupportedOperationException e){
                //TODO handle unsupported messages
                e.printStackTrace();
            }
        });
    }
}
