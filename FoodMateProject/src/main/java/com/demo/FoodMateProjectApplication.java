package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-private.properties")
public class FoodMateProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodMateProjectApplication.class, args);
	}

}
