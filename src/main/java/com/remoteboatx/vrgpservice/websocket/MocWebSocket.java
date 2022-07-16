package com.remoteboatx.vrgpservice.websocket;

public interface MocWebSocket {

    void sendMessage(String message);

    void close();
}
