package main.java.com.remoteboatx.vrgpservice;

import java.net.URI;
import java.net.URL;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import main.java.com.remoteboatx.vrgpservice.websocket.CentralWebSocketMessageHandler;
import main.java.com.remoteboatx.vrgpservice.websocket.WebSocketConnectionInterface;

@SpringBootApplication
public class VrgpServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(VrgpServiceApplication.class, args);


		
		//TODO testing only, move to a appropriate place later
		//WebSocketConnection.MakeConnection(new CentralWebSocketMessageHandler(),URI.create("ws://host.docker.internal:8080/vessel") );	
		String URL = "ws://127.0.0.1:8081/vessel";
		WebSocketConnectionInterface.MakeConnection(new CentralWebSocketMessageHandler(),URI.create(URL));
		System.out.println(URL);
	}

}
