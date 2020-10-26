package com.ratingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource
public class RatingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(RatingAppApplication.class, args);
	}

}
