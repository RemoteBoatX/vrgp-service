package com.remoteboatx.vrgpservice;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class BridgeWebSocket {
    private final WebSocketClient moc;

    BridgeWebSocket(String URL_moc) {
        moc = new WebSocketClient(URI.create(URL_moc)) {
            
            //Messages MOC --> VRGP Service
            @Override
            public void onMessage(String message) {
                System.out.println(message);
            }

            @Override
            public void onOpen(ServerHandshake handshake) {
                System.out.println("opened connection");
                moc.send("{\"name\": vrgp service , \"message\": hello}"); //sends a message
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.out.println("closed connection");
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
            }

        };
        moc.connect();

    }



    public WebSocketClient getClient() {
        return moc;
    }
}
