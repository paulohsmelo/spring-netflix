package com.paulomelo.carserver.service;

import com.paulomelo.carserver.domain.Car;
import com.paulomelo.carserver.exception.CarNotFoundException;
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
        return findCars().stream().filter(c -> c.getModel().equalsIgnoreCase(model)).findFirst().orElseThrow(() -> new CarNotFoundException(model));
    }

    private List<Car> findCars() {
        // TODO integrate with noSQL database
        return asList(new Car("Monza"), new Car("Palio"), new Car("Corsa"), new Car("Spin"));
    }
}
