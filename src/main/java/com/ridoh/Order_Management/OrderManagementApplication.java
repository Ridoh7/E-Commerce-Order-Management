package com.ridoh.Order_Management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point for the Order Management Application.
 * This class bootstraps the Spring Boot application.
 */
@SpringBootApplication
public class OrderManagementApplication {

	/**
	 * Main method that starts the Spring Boot application.
	 *
	 * @param args command-line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(OrderManagementApplication.class, args);
	}
}
