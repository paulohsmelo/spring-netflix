package com.paulomelo.carserver.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CarService {

    private final List<String> cars = Arrays.asList("Monza", "Palio", "Corsa", "Spin");

    public List<String> getAllCars() {
        return cars;
    }

    public String getCar(String car) {
        final int index = cars.indexOf(car);
        return index < 0 ? null : cars.get(index);
    }
}
