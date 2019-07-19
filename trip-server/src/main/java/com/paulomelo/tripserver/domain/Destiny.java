package com.paulomelo.tripserver.domain;

public class Destiny {

    private String location;

    private String recommendedCar;

    public Destiny(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public String getRecommendedCar() {
        return recommendedCar;
    }

    public void setRecommendedCar(String recommendedCar) {
        this.recommendedCar = recommendedCar;
    }
}
