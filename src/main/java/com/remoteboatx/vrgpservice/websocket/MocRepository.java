package com.remoteboatx.vrgpservice.websocket;

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
}
