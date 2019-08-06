package com.paulomelo.carserver.controller;

import com.paulomelo.carserver.dto.CarRequest;
import com.paulomelo.carserver.dto.CarResponse;
import com.paulomelo.carserver.dto.ErrorResponse;
import com.paulomelo.carserver.exception.CarAlreadyRegistered;
import com.paulomelo.carserver.exception.CarNotFoundException;
import com.paulomelo.carserver.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/cars")
@Validated
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

    @PostMapping(path = "/new", produces = "application/json", consumes = "application/json")
    public ResponseEntity<CarResponse> newCar(@Valid @RequestBody CarRequest carRequest) {
        try {
            return ResponseEntity.ok(CarResponse.withData(carService.newCar(carRequest.getModel())));
        } catch (CarAlreadyRegistered ex) {
            return ResponseEntity.badRequest().body(CarResponse.withError(new ErrorResponse(ex.getMessage())));
        }
    }

}
