package com.paulomelo.tripserver.exception;

public class DestinyAlreadyRegisteredException extends RuntimeException {

    public DestinyAlreadyRegisteredException(String destiny) {
        super("Destiny " + destiny + " already registered");
    }
}
