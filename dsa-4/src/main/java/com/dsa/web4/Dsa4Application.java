package com.dsa.web4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// JPA Auditing 기능을 활성화
@EnableJpaAuditing
@SpringBootApplication
public class Dsa4Application {

	public static void main(String[] args) {
		SpringApplication.run(Dsa4Application.class, args);
	}

}
