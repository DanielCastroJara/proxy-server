package com.example.proxyserver.client;

import com.example.proxyserver.model.HttpMessage;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RestClient implements ProxyClient{

    @Override
    public HttpMessage peek(HttpMessage message) {
        return null;
    }
}
