package com.remoteboatx.vrgpservice.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.remoteboatx.vrgpservice.Vessel;
import com.remoteboatx.vrgpservice.VrgpState;
import com.remoteboatx.vrgpservice.message.VesselInfoMessage;
import com.remoteboatx.vrgpservice.message.VrgpMessageType;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;


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

        // ask to authenticate else send vessel info

        //get vessel information
        Vessel v = VrgpState.getInstance().getVessel();


        //vessel information is not received
        if(!v.getMmsi().isEmpty()){

            try {

                //Create a vessel information message
                VesselInfoMessage infoMessage = new VesselInfoMessage(v);
                String message = new ObjectMapper().writeValueAsString(infoMessage);

                // send the message
                session.sendMessage(new TextMessage(message));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            //TODO handle if vessel info is not received

            // request vessel information from adapter
        }

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
