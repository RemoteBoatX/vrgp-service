package com.remoteboatx.vrgpservice;

import com.remoteboatx.vrgpservice.websocket.ClientWebSocketMessageHandlerWrapper;
import com.remoteboatx.vrgpservice.websocket.WebSocketMessageHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class VrgpServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(VrgpServiceApplication.class, args);


		//TODO testing only, move to a appropriate place later
		try {
			new ClientWebSocketMessageHandlerWrapper(new WebSocketMessageHandler(),
					WebSocketMessageHandler.ConnectionType.MOC,
					URI.create("ws://host.docker.internal:8080/vessel")); //moc client
		} catch (ExecutionException | InterruptedException e) {
			e.printStackTrace();
		}

	}

}
