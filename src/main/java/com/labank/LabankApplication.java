package com.labank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ComponentScan("com")
@EnableJpaRepositories("com")
@EntityScan("com")
@EnableAutoConfiguration
@SpringBootApplication
public class LabankApplication {

	public static void main(String[] args) {
		SpringApplication.run(LabankApplication.class, args);
	}

}

