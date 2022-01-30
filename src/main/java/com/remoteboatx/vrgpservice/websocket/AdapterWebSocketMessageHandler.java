package com.remoteboatx.vrgpservice.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.remoteboatx.vrgpservice.VrgpState;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.net.URI;
import java.util.concurrent.ExecutionException;


/**
 * WebSocket message handler for handling VRGP messages from the Adapter
 */
public class AdapterWebSocketMessageHandler extends WebSocketMessageHandler {


    public AdapterWebSocketMessageHandler() {

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        super.afterConnectionEstablished(session);

        VrgpState.getInstance().setAdapterSession(session);

        System.out.println("Adapter connected");
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

                //TODO this is testing, change to a connect handler method
                if(messageKey.equals("connect")){


                    //TODO check for vessel info
                    //connect to moc
                    try {
                        //TODO change to a dynamic way of retrieving the moc URI
                        new WebSocketClient(this, URI.create("ws://host.docker.internal:8080/vessel"));

                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }catch (UnsupportedOperationException e){
                //TODO handle unsupported messages
                e.printStackTrace();
            }
        });
    }
}
