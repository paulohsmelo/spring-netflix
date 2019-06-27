package com.paulomelo.carserver.service;

import com.paulomelo.carserver.domain.Car;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@Service
public class CarService {

    public List<Car> getAllCars() {
        return findCars();
    }

    public Car getCar(String model) {
        final Optional<Car> car = findCars().stream().filter(c -> c.getModel().equalsIgnoreCase(model)).findFirst();
        return car.isPresent() ? car.get() : null;
    }

    private List<Car> findCars() {
        return asList(new Car("Monza"), new Car("Palio"), new Car("Corsa"), new Car("Spin"));
    }
}
