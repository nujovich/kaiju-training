/*
 * Software property of Acquisio. Copyright 2003-2019.
 */
package com.leniolabs.training.integration.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

@Service
public class EndpointUtil {
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    protected ObjectMapper mapper;

    private MockMvc mockMvc;

    public MockMvc setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        return this.mockMvc;
    }

    public ResultActions getCall(String path, MultiValueMap<String, String> params) throws Exception {
        return mockMvc.perform(addBasicInformation(MockMvcRequestBuilders.get(path).params(params)));
    }

    public ResultActions getCall(String path) throws Exception {
        return mockMvc.perform(addBasicInformation(MockMvcRequestBuilders.get(path)));
    }

    public ResultActions postCall(String path, Object content) throws Exception {
        return mockMvc.perform(addBasicInformation(MockMvcRequestBuilders.post(path).content(mapper.writeValueAsString(content))));
    }

    public <T> T getResponseObject(MvcResult result, Class<T> t) throws IOException {
        String contentAsString = result.getResponse().getContentAsString();
        return mapper.readValue(contentAsString, t);
    }

    public <T> List<T> getResponseListOfObjects(MvcResult result, Class<T> t) throws IOException {
        String contentAsString = result.getResponse().getContentAsString();
        return (List<T>) mapper.readValue(contentAsString, List.class).stream().map(campaign -> mapper.convertValue(campaign, t)).collect(Collectors.toList());
    }

    public ResultActions deleteCall(String path) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.delete(path));
    }

    public ResultActions deleteCall(String path, Object content) throws Exception {
        return mockMvc.perform(addBasicInformation(MockMvcRequestBuilders.delete(path).content(mapper.writeValueAsString(content))));
    }

    public ResultActions putCall(String path, Object content) throws Exception {
        return mockMvc.perform(addBasicInformation(MockMvcRequestBuilders.put(path).content(mapper.writeValueAsString(content))));
    }

    private MockHttpServletRequestBuilder addBasicInformation(MockHttpServletRequestBuilder mockHttpServletRequestBuilder) {
        return mockHttpServletRequestBuilder
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }

}