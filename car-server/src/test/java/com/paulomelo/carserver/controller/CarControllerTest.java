package com.paulomelo.carserver.controller;

import org.hamcrest.Matchers;
import org.junit.Assert;
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
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CarControllerTest {

    private static final String GET_ALL = "/cars";
    private static final String GET_BY_MODEL = "/cars/model";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllSuccess() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(GET_ALL);
        final MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        Assert.assertThat(response.getStatus(), Matchers.equalTo(200));
        JSONAssert.assertEquals(parseJSON("getAllSuccessResponse.json"), response.getContentAsString(), JSONCompareMode.NON_EXTENSIBLE);
    }

    private String parseJSON(String resource) throws Exception {
        final File file = ResourceUtils.getFile("classpath:com.paulomelo.carserver.controller/" + resource);
        return new String(Files.readAllBytes(file.toPath()));
    }
}
