
package main.java.com.remoteboatx.vrgpservice.websocket;

import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface WebSocketConnectionInterface {

    /**
     * Establish connection 
     * @param messageHandler handler for the connection
     * @param mocUri URL to connect to
     */
    public static void MakeConnection(TextWebSocketHandler messageHandler,URI mocUri){}

}
