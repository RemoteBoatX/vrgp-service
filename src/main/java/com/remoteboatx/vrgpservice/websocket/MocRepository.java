package com.remoteboatx.vrgpservice.websocket;

import java.util.HashMap;
import java.util.Map;

public class MocRepository {

    private final Map<String, MocWebSocketMessageHandler> mocs = new HashMap<>();

    public void connectToMoc(String url) {
        final MocWebSocketMessageHandler mocMessageHandler = new MocWebSocketMessageHandler(url);
        mocs.put(url, mocMessageHandler);
    }

    public void disconnectFromMoc(String url) {
        // TODO: Implement
    }
}
