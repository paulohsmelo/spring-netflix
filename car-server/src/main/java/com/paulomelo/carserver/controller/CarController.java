package com.paulomelo.carserver.controller;

import com.paulomelo.carserver.domain.Car;
import com.paulomelo.carserver.dto.CarResponse;
import com.paulomelo.carserver.dto.ErrorResponse;
import com.paulomelo.carserver.exception.CarNotFoundException;
import com.paulomelo.carserver.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<CarResponse> getAllCars() {
        return ResponseEntity.ok(CarResponse.withData(carService.getAllCars()));
    }

    @GetMapping("/{model}")
    public ResponseEntity<CarResponse> getCarByModel(@PathVariable("model") String model) {
        try {
            return ResponseEntity.ok(CarResponse.withData(carService.getCar(model)));
        } catch (CarNotFoundException ex) {
            return ResponseEntity.badRequest().body(CarResponse.withError(new ErrorResponse(ex.getMessage())));
        }
    }

}
