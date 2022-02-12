package main.java.com.remoteboatx.vrgpservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VrgpServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(VrgpServiceApplication.class, args);

		
		
		/*
		//TODO testing only, move to a appropriate place later
		try {
			//TODO change to an appropriate address or some application property
			new WebSocketClient(new MocWebSocketMessageHandler(),
					URI.create("ws://host.docker.internal:8080/vessel")); //moc client
		} catch (ExecutionException | InterruptedException e) {
			e.printStackTrace();
		}
		*/
	}

}
