package com.summer_project_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SummerProjectBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(SummerProjectBackendApplication.class, args);
	}
}
