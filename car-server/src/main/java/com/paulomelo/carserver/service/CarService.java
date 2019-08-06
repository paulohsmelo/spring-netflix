package com.paulomelo.carserver.service;

import com.paulomelo.carserver.domain.Car;
import com.paulomelo.carserver.exception.CarAlreadyRegistered;
import com.paulomelo.carserver.exception.CarNotFoundException;
import com.paulomelo.carserver.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCar(String model) {
        return carRepository.findByModel(model).stream().filter(c -> c.getModel().equalsIgnoreCase(model)).findFirst().orElseThrow(() -> new CarNotFoundException(model));
    }

    public Car newCar(String model) {
        if (!carRepository.findByModel(model).isEmpty()) {
            throw new CarAlreadyRegistered(model);
        }

        return carRepository.insert(new Car(model));
    }

}
