package com.paulomelo.carserver.dto;

import javax.validation.constraints.NotNull;

public class CarRequest {

    @NotNull
    private String model;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

}
