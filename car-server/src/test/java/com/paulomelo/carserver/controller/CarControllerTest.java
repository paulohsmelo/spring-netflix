package com.paulomelo.carserver.controller;

import com.paulomelo.carserver.domain.Car;
import com.paulomelo.carserver.exception.CarAlreadyRegistered;
import com.paulomelo.carserver.exception.CarNotFoundException;
import com.paulomelo.carserver.service.CarService;
import org.hamcrest.Matchers;
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
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CarControllerTest {

    private static final String GET_ALL = "/cars";
    private static final String GET_BY_MODEL = "/cars/{model}";
    private static final String POST_NEW = "/cars/new";

    @MockBean
    private CarService carService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllSuccess() throws Exception {
        when(carService.getAllCars()).thenReturn(asList(new Car("Monza"), new Car("Palio"), new Car("Corsa"), new Car("Spin")));

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(GET_ALL);
        final MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        assertThat(response.getStatus(), Matchers.equalTo(200));
        JSONAssert.assertEquals(parseJSON("getAllSuccessResponse.json"), response.getContentAsString(), JSONCompareMode.NON_EXTENSIBLE);

        verify(carService).getAllCars();
    }

    @Test
    public void getByModelSuccess() throws Exception {
        when(carService.getCar("Monza")).thenReturn(new Car("Monza"));

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(GET_BY_MODEL, "Monza");
        final MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        assertThat(response.getStatus(), Matchers.equalTo(200));
        JSONAssert.assertEquals(parseJSON("getByModelSuccessResponse.json"), response.getContentAsString(), JSONCompareMode.NON_EXTENSIBLE);

        verify(carService).getCar("Monza");
    }

    @Test
    public void getByModelFail() throws Exception {
        when(carService.getCar("Invalid")).thenThrow(new CarNotFoundException("Invalid"));

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(GET_BY_MODEL, "Invalid");
        final MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        assertThat(response.getStatus(), Matchers.equalTo(400));
        JSONAssert.assertEquals(parseJSON("getByModelFailResponse.json"), response.getContentAsString(), JSONCompareMode.NON_EXTENSIBLE);

        verify(carService).getCar("Invalid");
    }

    @Test
    public void newCar() throws Exception {
        final Car spin = new Car("Spin");
        when(carService.newCar("Spin")).thenReturn(spin);

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(POST_NEW)
                .contentType(MediaType.APPLICATION_JSON)
                .content(parseJSON("postNewSuccessRequest.json"));
        final MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        assertThat(response.getStatus(), Matchers.equalTo(200));
        JSONAssert.assertEquals(parseJSON("postNewSuccessResponse.json"), response.getContentAsString(), JSONCompareMode.NON_EXTENSIBLE);

        verify(carService).newCar("Spin");
    }

    @Test
    public void newCarAlreadyRegistered() throws Exception {
        when(carService.newCar("Pajero")).thenThrow(new CarAlreadyRegistered("Pajero"));

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(POST_NEW)
                .contentType(MediaType.APPLICATION_JSON)
                .content(parseJSON("postNewCarAlreadyRegisteredRequest.json"));
        final MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        assertThat(response.getStatus(), Matchers.equalTo(400));
        JSONAssert.assertEquals(parseJSON("postNewCarAlreadyRegisteredResponse.json"), response.getContentAsString(), JSONCompareMode.NON_EXTENSIBLE);

        verify(carService).newCar("Pajero");
    }

    private String parseJSON(String resource) throws Exception {
        final File file = ResourceUtils.getFile("classpath:com.paulomelo.carserver.controller/" + resource);
        return new String(Files.readAllBytes(file.toPath()));
    }
}
