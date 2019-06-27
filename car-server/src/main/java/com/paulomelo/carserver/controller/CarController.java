package com.paulomelo.carserver.controller;

import com.paulomelo.carserver.domain.Car;
import com.paulomelo.carserver.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Car>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    @GetMapping("/{model}")
    public Car getCarByModel(@PathVariable("model") String model) {
        final Car car = carService.getCar(model);
        return car == null ? new Car("Model " + model + " doesn't exists") : car;
    }

}
