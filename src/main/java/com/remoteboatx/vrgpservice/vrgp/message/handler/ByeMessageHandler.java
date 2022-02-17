package com.remoteboatx.vrgpservice.vrgp.message.handler;

import com.remoteboatx.vrgpservice.vrgp.message.ByeMessage;
import com.remoteboatx.vrgpservice.vrgp.message.VrgpMessage;
import com.remoteboatx.vrgpservice.websocket.MocWebSocket;

import java.util.function.Function;

/**
 * Message handler for VRGP {@link ByeMessage}s.
 */
public class ByeMessageHandler implements VrgpSingleMessageHandler<ByeMessage> {

    @Override
    public void handleMessage(ByeMessage message, MocWebSocket mocWebSocket) {
        // TODO: Implement.
    }

    @Override
    public Function<VrgpMessage, ByeMessage> getSingleMessage() {
        return VrgpMessage::getBye;
    }
}
