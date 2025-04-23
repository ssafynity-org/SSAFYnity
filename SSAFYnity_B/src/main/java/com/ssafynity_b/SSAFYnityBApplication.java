package com.ssafynity_b;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
public class SSAFYnityBApplication {

	public static void main(String[] args) {
		SpringApplication.run(SSAFYnityBApplication.class, args);
	}

}
