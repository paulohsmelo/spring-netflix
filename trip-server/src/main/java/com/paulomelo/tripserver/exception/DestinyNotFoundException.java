package com.paulomelo.tripserver.exception;

public class DestinyNotFoundException extends RuntimeException {

    public DestinyNotFoundException(String destiny) {
        super("Destiny " + destiny + " doesn't exists");
    }
}
