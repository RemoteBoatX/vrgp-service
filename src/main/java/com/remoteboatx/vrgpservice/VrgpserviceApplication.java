package com.remoteboatx.vrgpservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VrgpserviceApplication {

	public static void main(String[] args) {

		SpringApplication.run(VrgpserviceApplication.class, args);
		System.out.println("started");
		BridgeWebSocket con = new BridgeWebSocket("ws://host.docker.internal:8080/ws"); //opens connection with the moc
	}

}
