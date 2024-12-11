package com.popcorn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SwaggerAPIDocxApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwaggerAPIDocxApplication.class, args);
	}

}
