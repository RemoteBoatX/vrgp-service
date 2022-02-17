package com.remoteboatx.vrgpservice;

import com.remoteboatx.vrgpservice.websocket.CentralWebSocketMessageHandler;
import com.remoteboatx.vrgpservice.websocket.WebSocketConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;

@SpringBootApplication
public class VrgpServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(VrgpServiceApplication.class, args);

        //TODO testing only, move to a appropriate place later
        //WebSocketConnection.MakeConnection(new CentralWebSocketMessageHandler(),URI.create("ws://host.docker.internal:8080/vessel") );
        String URL = "ws://localhost:8081/vessel";
        WebSocketConnection.MakeConnection(new CentralWebSocketMessageHandler(), URI.create(URL));
        System.out.println(URL);
    }

}
