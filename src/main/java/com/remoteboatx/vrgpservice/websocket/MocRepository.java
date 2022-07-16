package com.remoteboatx.vrgpservice.websocket;

import com.remoteboatx.vrgpservice.vrgp.message.VrgpMessage;
import com.remoteboatx.vrgpservice.vrgp.message.util.JsonUtil;

import java.util.HashMap;
import java.util.Map;

public class MocRepository {

    private final Map<String, MocWebSocketConnection> mocs = new HashMap<>();

    public void connectToMoc(String url) {
        final MocWebSocketConnection moc = new MocWebSocketConnection(url);
        mocs.put(url, moc);
    }

    public void disconnectFromMoc(String url) {
        final MocWebSocketConnection moc = mocs.remove(url);
        moc.close();
    }

    public void sendMessageToMoc(String url, VrgpMessage msg){
        final MocWebSocketConnection moc = mocs.get(url);

        final String message = JsonUtil.toJsonString(msg);
        moc.sendMessage(message);
    }
}
