package com.paulomelo.tripserver.service;

import com.paulomelo.tripserver.domain.Destiny;
import com.paulomelo.tripserver.exception.DestinyNotFoundException;
import com.paulomelo.tripserver.repository.DestinyRepository;
import com.paulomelo.tripserver.service.DestinyService;
import com.paulomelo.tripserver.service.TripServiceIntegration;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DestinyServiceTest {

    private DestinyService service;
    private DestinyRepository destinyRepositoryMock;
    private TripServiceIntegration tripServiceIntegrationMock;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {
        destinyRepositoryMock = mock(DestinyRepository.class);
        tripServiceIntegrationMock = mock(TripServiceIntegration.class);
        service = new DestinyService(destinyRepositoryMock, tripServiceIntegrationMock);
    }

    @Test
    public void getAllDestinies() {
        final List<Destiny> allDestinies = service.getAllDestinies();
        assertNotNull(allDestinies);
    }

    @Test
    public void givenInvalidDestinyThenThrowException() {
        expectedException.expect(DestinyNotFoundException.class);
        service.getDestiny("fooDestiny");
    }

    @Test
    public void givenBeachThenReturnDestiny() {
        when(destinyRepositoryMock.findByLocation("Beach")).thenReturn(asList(new Destiny("Beach")));

        final Destiny destiny = service.getDestiny("Beach");
        assertEquals("Beach", destiny.getLocation());

        verify(destinyRepositoryMock).findByLocation("Beach");
    }

    @Test
    public void validateRecommendedCar() {
        when(destinyRepositoryMock.findByLocation("Beach")).thenReturn(asList(new Destiny("Beach")));
        when(tripServiceIntegrationMock.getCar("Corsa")).thenReturn("Corsa");

        final Destiny destiny = service.getDestiny("Beach");
        assertEquals("Corsa", destiny.getRecommendedCar());

        verify(destinyRepositoryMock).findByLocation("Beach");
        verify(tripServiceIntegrationMock).getCar("Corsa");
    }

}
