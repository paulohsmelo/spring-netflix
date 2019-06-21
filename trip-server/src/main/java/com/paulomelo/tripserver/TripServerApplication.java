package com.paulomelo.tripserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@EnableDiscoveryClient
@EnableCircuitBreaker
@SpringBootApplication
public class TripServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TripServerApplication.class, args);
	}

}
