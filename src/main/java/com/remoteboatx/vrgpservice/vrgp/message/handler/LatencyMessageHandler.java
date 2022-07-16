package com.remoteboatx.vrgpservice.vrgp.message.handler;

import com.remoteboatx.vrgpservice.state.Latency;
import com.remoteboatx.vrgpservice.vrgp.message.LatencyMessage;
import com.remoteboatx.vrgpservice.vrgp.message.VrgpMessage;
import com.remoteboatx.vrgpservice.websocket.MocWebSocket;

import java.util.Calendar;
import java.util.function.Function;

/**
 * Message handler for VRGP {@link LatencyMessage}s.
 */
public class LatencyMessageHandler implements VrgpSingleMessageHandler<LatencyMessage> {

    private final int LATENCY_THRESHOLD = 40; //TODO decide on the latency threshold

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

        final Latency latency = new Latency();
        latency.setOutgoing(received - sent);
        latency.setIncoming(now - received);

        System.out.println("rtt: " + latency.getRoundTrip());
    }

    private void handleMessageWithSentTimestamp(LatencyMessage message, MocWebSocket mocWebSocket) {
        final long now = Calendar.getInstance().getTimeInMillis();
        final long sent = message.getSent();

        final int latency = (int)(now - sent);

        if(latency > LATENCY_THRESHOLD){
            System.out.println("High latency");
            //TODO take action
        }

        final LatencyMessage reply = new LatencyMessage().withSent(sent).withReceived(now);
        mocWebSocket.sendMessage(new VrgpMessage().withTime(reply).toJson());
    }

    @Override
    public Function<VrgpMessage, LatencyMessage> getSingleMessage() {
        return VrgpMessage::getTime;
    }
}
