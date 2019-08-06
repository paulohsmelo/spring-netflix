package com.paulomelo.carserver.service;

import com.paulomelo.carserver.domain.Car;
import com.paulomelo.carserver.exception.CarAlreadyRegistered;
import com.paulomelo.carserver.exception.CarNotFoundException;
import com.paulomelo.carserver.repository.CarRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class CarServiceTest {

    private CarRepository carRepository;
    private CarService service;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        carRepository = mock(CarRepository.class);
        service = new CarService(carRepository);
    }

    @Test
    public void getAllCars() {
        when(carRepository.findAll()).thenReturn(asList(new Car("Corsa"), new Car("Monza")));
        final List<Car> allCars = service.getAllCars();
        assertNotNull(allCars);
    }

    @Test
    public void givenNonexistentCarThenThrowException() {
        when(carRepository.findByModel("fooCar")).thenReturn(new ArrayList<>());
        exception.expect(CarNotFoundException.class);
        service.getCar("fooCar");
    }

    @Test
    public void givenCorsaThenReturnCorsa() {
        when(carRepository.findByModel("Corsa")).thenReturn(asList(new Car("Corsa")));
        final Car corsa = service.getCar("Corsa");
        assertEquals("Corsa", corsa.getModel());
    }

    @Test
    public void givenNewCarThenShouldRegister() {
        when(carRepository.insert(any(Car.class))).thenReturn(new Car("Palio"));
        final Car newCar = service.newCar("Palio");
        assertEquals("Palio", newCar.getModel());
        verify(carRepository).findByModel("Palio");
        verify(carRepository).insert(any(Car.class));
    }

    @Test
    public void givenCarAlreadyRegisteredThenThrowException() {
        when(carRepository.findByModel("Corsa")).thenReturn(asList(new Car("Corsa")));
        exception.expect(CarAlreadyRegistered.class);
        final Car newCar = service.newCar("Corsa");
        verify(carRepository).findByModel("Corsa");
        verifyZeroInteractions(carRepository.insert(any(Car.class)));
    }
}
