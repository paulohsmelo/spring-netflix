package com.paulomelo.tripserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class TripServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TripServerApplication.class, args);
	}

}
