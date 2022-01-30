package com.remoteboatx.vrgpservice.message.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Calendar;

/**
 * Message handler for VRGP latency messages.
 */
public class LatencyMessageHandler implements VrgpMessageHandler {

    @Override
    public void handleMessage(WebSocketSession mocSession, Object message) {

        if(message instanceof JsonNode) {

            JsonNode jsonMessage = (JsonNode) message;
            long now = Calendar.getInstance().getTimeInMillis();
            Long sent = Long.valueOf(String.valueOf(jsonMessage.get("sent")));

            System.out.printf("{\"time\": {\"sent\": %d, \"received\": %d}}%n",
                    sent, now);
            try {

                mocSession.sendMessage(new TextMessage(
                        String.format("{\"time\": {\"sent\": %d, \"received\": %d}}",
                                sent, now)));
            } catch (IOException e) {
                //TODO add handling
                e.printStackTrace();
            }

        }else{
            //TODO handle
        }
    }
}
