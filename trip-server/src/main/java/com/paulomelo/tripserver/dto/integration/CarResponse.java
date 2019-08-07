package com.paulomelo.tripserver.dto.integration;

public class CarResponse {

    private String model;

    public CarResponse defaultCarRecommendation() {
        this.model = "No car recommended";
        return this;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
