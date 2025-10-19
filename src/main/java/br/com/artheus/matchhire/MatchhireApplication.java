package br.com.artheus.matchhire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MatchhireApplication {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(MatchhireApplication.class);
		app.setAdditionalProfiles("no-security"); // opcional
		app.run(args);
	}
}

