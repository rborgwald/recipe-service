package com.bufkes.service.recipe;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecipeApplication {

	static final Logger logger = LogManager.getLogger(RecipeApplication.class.getName());
	
	public static void main(String[] args) {
		logger.info("Recipe application started");
		SpringApplication.run(RecipeApplication.class, args);
	}
}
