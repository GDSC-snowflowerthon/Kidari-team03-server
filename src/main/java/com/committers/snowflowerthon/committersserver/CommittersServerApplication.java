package com.committers.snowflowerthon.committersserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
public class CommittersServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommittersServerApplication.class, args);
	}

}
