package com.remoteboatx.moc;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class BridgeWebSocket {
    private WebSocketSession adapter_side; 
    private WebSocketClient moc_side;

    BridgeWebSocket(WebSocketSession session,String  URL_moc)
    {
        adapter_side = session;

        moc_side = new WebSocketClient( URI.create(URL_moc) )
        {
                    @Override
                    public void onMessage( String message ) {
                        System.out.println( message );
                    }

                    @Override
                    public void onOpen( ServerHandshake handshake ) {
                        System.out.println( "opened connection" );
                    }

                    @Override
                    public void onClose( int code, String reason, boolean remote ) {
                        System.out.println( "closed connection" );
                    }

                    @Override
                    public void onError( Exception ex ) {
                        ex.printStackTrace();
                    }

        };
    }
    
    public WebSocketSession getSession(){
       return adapter_side;
    }

    public WebSocketClient getClient(){
        return moc_side;
    }
}

