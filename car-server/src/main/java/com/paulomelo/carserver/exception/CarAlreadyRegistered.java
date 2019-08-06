package com.paulomelo.carserver.exception;

public class CarAlreadyRegistered extends RuntimeException {

    public CarAlreadyRegistered(String model) {
        super("Model " + model + " already registered");
    }
}
