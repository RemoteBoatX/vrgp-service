package com.remoteboatx.vrgpservice.vrgp.message.handler;

import com.remoteboatx.vrgpservice.vrgp.message.LatencyMessage;
import com.remoteboatx.vrgpservice.vrgp.message.VrgpMessage;
import com.remoteboatx.vrgpservice.websocket.MocWebSocket;

import java.util.Calendar;
import java.util.function.Function;

/**
 * Message handler for VRGP {@link LatencyMessage}s.
 */
public class LatencyMessageHandler implements VrgpSingleMessageHandler<LatencyMessage> {

    @Override
    public void handleMessage(LatencyMessage message, MocWebSocket mocWebSocket) {
        if (message.hasReceived()) {
            handleMessageWithSentAndReceivedTimestamp(message, mocWebSocket);
        } else {
            handleMessageWithSentTimestamp(message, mocWebSocket);
        }
    }

    private void handleMessageWithSentAndReceivedTimestamp(LatencyMessage message, MocWebSocket mocWebSocket) {
        final long now = Calendar.getInstance().getTimeInMillis();
        final long sent = message.getSent();
        final long received = message.getReceived();

        // TODO: Handle.
    }

    private void handleMessageWithSentTimestamp(LatencyMessage message, MocWebSocket mocWebSocket) {
        final long now = Calendar.getInstance().getTimeInMillis();
        final long sent = message.getSent();

        final LatencyMessage reply = new LatencyMessage().withSent(sent).withReceived(now);
        // TODO: Send reply.
    }

    @Override
    public Function<VrgpMessage, LatencyMessage> getSingleMessage() {
        return VrgpMessage::getTime;
    }
}
