package com.paulomelo.tripserver.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TripServiceIntegration {

    private final RestTemplate restTemplate;

    public TripServiceIntegration(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "getCarFallback")
    public String getCar(String model) {
        // TODO refactor URL String to property
        return restTemplate.getForEntity("http://car-server/cars/" + model, String.class).getBody();
    }

    public String getCarFallback(String model) {
        return "No car recommended";
    }
}
