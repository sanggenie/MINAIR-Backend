package com.minair;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MinairApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinairApplication.class, args);
	}

}
