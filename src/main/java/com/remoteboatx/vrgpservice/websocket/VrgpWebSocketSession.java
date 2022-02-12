package com.remoteboatx.vrgpservice.websocket;

import org.springframework.web.socket.WebSocketSession;

import java.net.URI;

public class VrgpWebSocketSession {

    private final SessionType type;
    private final WebSocketSession session;
    private URI uri;

    public VrgpWebSocketSession(SessionType type, WebSocketSession session, URI uri) {
        this.type = type;
        this.session = session;
        this.uri = uri;
    }

    public SessionType getType() {
        return type;
    }

    public WebSocketSession getSession() {
        return session;
    }

    public URI getUri() {
        return uri;
    }

    public enum SessionType{
        MOC(), ADAPTER()
    }
}
