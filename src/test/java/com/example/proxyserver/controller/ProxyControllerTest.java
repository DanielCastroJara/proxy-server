package com.example.proxyserver.controller;

import com.example.proxyserver.controller.objects.HttpObjectsTest;
import com.example.proxyserver.service.ProxyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProxyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProxyService service;

    @Test
    public void endPointIsOpen() throws Exception {
        when(service.redirect(
                HttpObjectsTest.VALID_BASE_TARGET,
                HttpObjectsTest.getValidBodyObject(objectMapper),
                new MockHttpServletRequest("GET", HttpObjectsTest.VALID_COMPLETE_URL)))
                .thenReturn(HttpObjectsTest.validResponse());
        mockMvc.perform(get(HttpObjectsTest.VALID_COMPLETE_URL)
                        .content(objectMapper.writeValueAsString(HttpObjectsTest.getValidBodyObject(objectMapper)))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void targetValueIsMandatory() throws Exception {
        when(service.redirect(
                "",
                HttpObjectsTest.getValidBodyObject(objectMapper),
                new MockHttpServletRequest("GET", HttpObjectsTest.VALID_COMPLETE_URL)))
                .thenReturn(HttpObjectsTest.validResponse());
        mockMvc.perform(get(HttpObjectsTest.REDIRECT_ENDPOINT)
                        .content(objectMapper.writeValueAsString(HttpObjectsTest.getValidBodyObject(objectMapper)))
                        .contentType("application/json"))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void bodyValueIsOptional() throws Exception {
        when(service.redirect(
                HttpObjectsTest.VALID_BASE_TARGET,
                HttpObjectsTest.getValidBodyObject(null),
                new MockHttpServletRequest("GET", HttpObjectsTest.VALID_COMPLETE_URL)))
                .thenReturn(HttpObjectsTest.validResponse());
        mockMvc.perform(get(HttpObjectsTest.VALID_COMPLETE_URL)
                        .content(objectMapper.writeValueAsString(HttpObjectsTest.getValidBodyObject(objectMapper)))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
    }
}
