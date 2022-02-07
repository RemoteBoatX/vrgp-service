package com.remoteboatx.vrgpservice.websocket;

import org.springframework.web.socket.WebSocketSession;

public class VrgpWebSocketSession {

    private SessionType type;
    private WebSocketSession session;

    public VrgpWebSocketSession(SessionType type, WebSocketSession session) {
        this.type = type;
        this.session = session;
    }

    public SessionType getType() {
        return type;
    }

    public WebSocketSession getSession() {
        return session;
    }

    public enum SessionType{
        MOC(), ADAPTER()
    }
}
