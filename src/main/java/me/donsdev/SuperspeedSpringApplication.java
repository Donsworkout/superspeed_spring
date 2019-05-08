package me.donsdev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SuperspeedSpringApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SuperspeedSpringApplication.class);
		app.run(args);
	}

}
