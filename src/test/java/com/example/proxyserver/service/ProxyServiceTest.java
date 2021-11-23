package com.example.proxyserver.service;

import com.example.proxyserver.client.ProxyClient;
import com.example.proxyserver.controller.objects.HttpObjectsTest;
import com.example.proxyserver.controller.objects.ProxyClientObjectTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProxyServiceTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ProxyService proxyService;

    @Test
    public void validClientResponse() throws IOException, InterruptedException {
        ProxyClient proxyClient = Mockito.mock(ProxyClient.class);
        Mockito.when(proxyClient.peek(ProxyClientObjectTest.getValidRequest()))
                .thenReturn(ProxyClientObjectTest.getValidResponse());
        ResponseEntity<String> response = proxyService.redirect(
                HttpObjectsTest.VALID_BASE_TARGET,
                HttpObjectsTest.getValidBodyObject(objectMapper),
                new MockHttpServletRequest("GET", HttpObjectsTest.VALID_COMPLETE_URL));
        assertEquals(302, response.getStatusCodeValue());
        assertThat(0 < response.getHeaders().size());
    }

    @Test
    public void redirectClientResponse() throws IOException, InterruptedException {
        ProxyClient proxyClient = Mockito.mock(ProxyClient.class);
        Mockito.when(proxyClient.peek(ProxyClientObjectTest.getValidRequest()))
                .thenReturn(ProxyClientObjectTest.getValidResponse());
        ResponseEntity<String> response = proxyService.redirect(
                HttpObjectsTest.VALID_BASE_TARGET,
                HttpObjectsTest.getValidBodyObject(objectMapper),
                new MockHttpServletRequest("GET", HttpObjectsTest.VALID_COMPLETE_URL));
        assertEquals(302, response.getStatusCodeValue());
        assertThat(0 < response.getHeaders().size());
    }
}
