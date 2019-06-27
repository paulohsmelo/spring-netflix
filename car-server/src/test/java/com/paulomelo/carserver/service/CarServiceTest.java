package com.paulomelo.carserver.service;

import com.paulomelo.carserver.domain.Car;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CarServiceTest {

    private final CarService service = new CarService();

    @Test
    public void getAllCars() {
        final List<Car> allCars = service.getAllCars();

        assertNotNull(allCars);
    }

    @Test
    public void givenNonexistentCarThenReturnNull() {
        final Car fooCar = service.getCar("fooCar");

        assertNull(fooCar);
    }

    @Test
    public void givenCorsaThenReturnCorsa() {
        final Car corsa = service.getCar("Corsa");

        assertEquals("Corsa", corsa.getModel());
    }
}
