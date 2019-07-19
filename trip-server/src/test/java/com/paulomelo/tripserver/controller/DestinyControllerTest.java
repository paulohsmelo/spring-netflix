package com.paulomelo.tripserver.controller;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.InputStream;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DestinyControllerTest {

    private final static String GET_ALL = "/trip";
    private final static String GET_BY_DESTINY = "/trip/{destiny}";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllDestiniesSuccess() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(GET_ALL);
        final MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        assertThat(response.getStatus(), equalTo(200));
        JSONAssert.assertEquals(parseJSON("getAllSuccessResponse.json"), response.getContentAsString(), JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    public void getByDestinySuccess() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(GET_BY_DESTINY, "Beach");
        final MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        assertThat(response.getStatus(), equalTo(200));
        JSONAssert.assertEquals(parseJSON("getByDestinySuccessResponse.json"), response.getContentAsString(), JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    public void getByDestinyFail() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(GET_BY_DESTINY, "fooDestiny");
        final MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        assertThat(response.getStatus(), equalTo(400));
        JSONAssert.assertEquals(parseJSON("getByDestinyFailResponse.json"), response.getContentAsString(), JSONCompareMode.NON_EXTENSIBLE);
    }

    private String parseJSON(String resource) throws Exception {
        final InputStream stream = getClass().getResourceAsStream(resource);
        return IOUtils.toString(stream);
    }
}
