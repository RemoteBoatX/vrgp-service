package com.remoteboatx.vrgpservice.message.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.remoteboatx.vrgpservice.VrgpState;
import com.remoteboatx.vrgpservice.models.Vessel;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;


public class VesselInfoMessageHandler implements VrgpMessageHandler {

    @Override
    public void handleMessage(WebSocketSession mocSession, Object message) {

        if(message instanceof JsonNode) {

            JsonNode jsonMessage = (JsonNode) message;
            try {

                //deserialize the json into a vessel object
                Vessel v = new ObjectMapper()
                        .readerFor(Vessel.class)
                        .readValue(jsonMessage.get("vessel"));

                VrgpState.getInstance().setVessel(v); //update vessel information

            } catch (IOException e) {
                e.printStackTrace();
            }


        }else{
            //TODO handle
        }

    }
}
