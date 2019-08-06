package com.paulomelo.tripserver.controller;

import com.paulomelo.tripserver.domain.Destiny;
import com.paulomelo.tripserver.exception.DestinyAlreadyRegisteredException;
import com.paulomelo.tripserver.exception.DestinyNotFoundException;
import com.paulomelo.tripserver.service.DestinyService;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.InputStream;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DestinyControllerTest {

    private final static String GET_ALL = "/trip";
    private final static String GET_BY_DESTINY = "/trip/{destiny}";
    private final static String POST_NEW_DESTINY = "/destiny/new";

    @MockBean
    private DestinyService destinyService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllDestiniesSuccess() throws Exception {
        when(destinyService.getAllDestinies()).thenReturn(asList(new Destiny("Beach"), new Destiny("Mountain"), new Destiny("Woods")));

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(GET_ALL);
        final MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        assertThat(response.getStatus(), equalTo(200));
        JSONAssert.assertEquals(parseJSON("getAllSuccessResponse.json"), response.getContentAsString(), JSONCompareMode.NON_EXTENSIBLE);

        verify(destinyService).getAllDestinies();
    }

    @Test
    public void getByDestinySuccess() throws Exception {
        final Destiny destiny = new Destiny("Beach");
        destiny.setRecommendedCar("No car recommended");
        when(destinyService.getDestiny("Beach")).thenReturn(destiny);

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(GET_BY_DESTINY, "Beach");
        final MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        assertThat(response.getStatus(), equalTo(200));
        JSONAssert.assertEquals(parseJSON("getByDestinySuccessResponse.json"), response.getContentAsString(), JSONCompareMode.NON_EXTENSIBLE);

        verify(destinyService).getDestiny("Beach");
    }

    @Test
    public void getByDestinyFail() throws Exception {
        when(destinyService.getDestiny("fooDestiny")).thenThrow(new DestinyNotFoundException("fooDestiny"));

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(GET_BY_DESTINY, "fooDestiny");
        final MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        assertThat(response.getStatus(), equalTo(400));
        JSONAssert.assertEquals(parseJSON("getByDestinyFailResponse.json"), response.getContentAsString(), JSONCompareMode.NON_EXTENSIBLE);

        verify(destinyService).getDestiny("fooDestiny");
    }

    @Test
    public void postNewDestinySuccess() throws Exception {
        final Destiny destiny = new Destiny("Paris");
        destiny.setRecommendedCar("Peugeot");
        when(destinyService.newDestiny("Paris")).thenReturn(destiny);

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(POST_NEW_DESTINY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(parseJSON("postNewDestinySuccessRequest.json"));
        final MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        assertThat(response.getStatus(), equalTo(200));
        JSONAssert.assertEquals(parseJSON("postNewDestinySuccessResponse.json"), response.getContentAsString(), JSONCompareMode.NON_EXTENSIBLE);

        verify(destinyService).newDestiny("Paris");
    }

    @Test
    public void postNewDestinyAlreadyRegistered() throws Exception {
        when(destinyService.newDestiny("Beach")).thenThrow(new DestinyAlreadyRegisteredException("Beach"));

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(POST_NEW_DESTINY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(parseJSON("postNewDestinyAlreadyRegisteredRequest.json"));
        final MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        assertThat(response.getStatus(), equalTo(400));
        JSONAssert.assertEquals(parseJSON("postNewDestinyAlreadyRegisteredResponse.json"), response.getContentAsString(), JSONCompareMode.NON_EXTENSIBLE);

        verify(destinyService).newDestiny("Beach");
    }

    private String parseJSON(String resource) throws Exception {
        final InputStream stream = getClass().getResourceAsStream(resource);
        return IOUtils.toString(stream);
    }
}
