package com.paulomelo.tripserver.service;

import com.paulomelo.tripserver.domain.Destiny;
import com.paulomelo.tripserver.exception.DestinyNotFoundException;
import com.paulomelo.tripserver.service.DestinyService;
import com.paulomelo.tripserver.service.TripServiceIntegration;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DestinyServiceTest {

    private DestinyService service;
    private TripServiceIntegration tripServiceIntegrationMock;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {
        tripServiceIntegrationMock = mock(TripServiceIntegration.class);
        service = new DestinyService(tripServiceIntegrationMock);
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
        final Destiny destiny = service.getDestiny("Beach");
        assertEquals("Beach", destiny.getLocation());
    }

    @Test
    public void validateRecommendedCar() {
        when(tripServiceIntegrationMock.getCar(anyString())).thenReturn("Corsa");
        final Destiny destiny = service.getDestiny("Beach");
        assertEquals("Corsa", destiny.getRecommendedCar());
    }
}
