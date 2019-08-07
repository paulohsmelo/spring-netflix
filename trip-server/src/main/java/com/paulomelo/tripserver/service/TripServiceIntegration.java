package com.paulomelo.tripserver.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.paulomelo.tripserver.dto.integration.CarResponse;
import com.paulomelo.tripserver.dto.integration.DataTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class TripServiceIntegration {

    private static final String URL_GET_CAR = "http://car-server/cars/{model}"; // TODO refactor URL String to property

    private final RestTemplate restTemplate;

    public TripServiceIntegration(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "getCarFallback")
    public CarResponse getCar(String model) {
        return restTemplate.exchange(URL_GET_CAR, HttpMethod.GET, null, new ParameterizedTypeReference<DataTemplate<CarResponse>>(){}, model)
                .getBody()
                .getData();
    }

    public CarResponse getCarFallback(String model) {
        return new CarResponse().defaultCarRecommendation();
    }
}
