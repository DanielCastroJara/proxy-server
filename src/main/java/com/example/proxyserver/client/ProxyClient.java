package com.example.proxyserver.client;

import com.example.proxyserver.model.HttpMessage;

import java.io.IOException;

public interface ProxyClient {

    HttpMessage peek(HttpMessage message) throws IOException, InterruptedException;


}
