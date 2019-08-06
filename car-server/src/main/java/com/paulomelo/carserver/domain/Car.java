package com.paulomelo.carserver.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cars")
public class Car {

    private String model;

    public Car(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }
}
