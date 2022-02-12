package com.remoteboatx.vrgpservice;

import com.remoteboatx.vrgpservice.websocket.VrgpWebSocketSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VrgpServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(VrgpServiceApplication.class, args);

		//TODO testing only, move to a appropriate place later
		VrgpState.getInstance().getVrgpWebsocketMessageHandler()
				.createWebSocketSession("ws://host.docker.internal:8080/vessel",
						VrgpWebSocketSession.SessionType.MOC);

	}

}
