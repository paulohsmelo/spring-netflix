package com.paulomelo.tripserver.service;

import com.paulomelo.tripserver.domain.Destiny;
import com.paulomelo.tripserver.exception.DestinyNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

@Service
public class DestinyService {

    private final TripServiceIntegration tripServiceIntegration;

    public DestinyService(TripServiceIntegration tripServiceIntegration) {
        this.tripServiceIntegration = tripServiceIntegration;
    }

    public List<Destiny> getAllDestinies() {
        return findDestinies();
    }

    public Destiny getDestiny(String destiny) {
        final Destiny des = findDestinies().stream().filter(d -> d.getLocation().equalsIgnoreCase(destiny)).findFirst().orElseThrow(() -> new DestinyNotFoundException(destiny));
        des.setRecommendedCar(tripServiceIntegration.getCar("Corsa"));
        return des;
    }

    private List<Destiny> findDestinies() {
        // TODO integrate with noSQL database
        return asList(new Destiny("Beach"), new Destiny("Moutain"), new Destiny("Woods"));
    }
}
