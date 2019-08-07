package com.paulomelo.tripserver.service;

import com.paulomelo.tripserver.domain.Destiny;
import com.paulomelo.tripserver.exception.DestinyAlreadyRegisteredException;
import com.paulomelo.tripserver.exception.DestinyNotFoundException;
import com.paulomelo.tripserver.repository.DestinyRepository;
import org.springframework.stereotype.Service;
import sun.security.krb5.internal.crypto.Des;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

@Service
public class DestinyService {

    private final DestinyRepository destinyRepository;
    private final TripServiceIntegration tripServiceIntegration;

    public DestinyService(DestinyRepository destinyRepository, TripServiceIntegration tripServiceIntegration) {
        this.destinyRepository = destinyRepository;
        this.tripServiceIntegration = tripServiceIntegration;
    }

    public List<Destiny> getAllDestinies() {
        return destinyRepository.findAll();
    }

    public Destiny getDestiny(String destiny) {
        final Destiny des = destinyRepository.findByLocation(destiny).stream().filter(d -> d.getLocation().equalsIgnoreCase(destiny)).findFirst().orElseThrow(() -> new DestinyNotFoundException(destiny));
        des.setRecommendedCar(tripServiceIntegration.getCar("Corsa").getModel());
        return des;
    }

    public Destiny newDestiny(String location) {
        if (!destinyRepository.findByLocation(location).isEmpty()) {
            throw new DestinyAlreadyRegisteredException(location);
        }
        return destinyRepository.insert(new Destiny(location));
    }
}
