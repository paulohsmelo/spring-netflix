package com.paulomelo.carserver.exception;

public class CarNotFoundException extends RuntimeException {

    public CarNotFoundException(String model) {
        super("Model " + model + " doesn't exists");
    }
}
