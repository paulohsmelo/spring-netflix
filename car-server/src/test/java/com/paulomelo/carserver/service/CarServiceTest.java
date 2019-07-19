package com.paulomelo.carserver.service;

import com.paulomelo.carserver.domain.Car;
import com.paulomelo.carserver.exception.CarNotFoundException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CarServiceTest {

    private final CarService service = new CarService();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void getAllCars() {
        final List<Car> allCars = service.getAllCars();
        assertNotNull(allCars);
    }

    @Test
    public void givenNonexistentCarThenThrowException() {
        exception.expect(CarNotFoundException.class);
        service.getCar("fooCar");
    }

    @Test
    public void givenCorsaThenReturnCorsa() {
        final Car corsa = service.getCar("Corsa");
        assertEquals("Corsa", corsa.getModel());
    }
}
