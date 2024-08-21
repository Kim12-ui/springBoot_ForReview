package com.dsa.web5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Dsa5Application {

	public static void main(String[] args) {
		SpringApplication.run(Dsa5Application.class, args);
	}

}
