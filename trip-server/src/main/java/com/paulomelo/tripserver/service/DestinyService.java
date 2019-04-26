package com.paulomelo.tripserver.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DestinyService {

    private final List<String> destinies = Arrays.asList("Beach", "Mountain", "Woods");

    private final TripServiceIntegration tripServiceIntegration;

    public DestinyService(TripServiceIntegration tripServiceIntegration) {
        this.tripServiceIntegration = tripServiceIntegration;
    }

    public List<String> getAllDestinies() {
        return destinies;
    }

    public String getDestiny(String destiny) {
        final int index = destinies.indexOf(destiny);
        if (index < 0) {
            return "Destiny doesn't exists";
        }

        return destinies.get(index) + " - Recommended car: " + tripServiceIntegration.getCar("Corsa");
    }
}
