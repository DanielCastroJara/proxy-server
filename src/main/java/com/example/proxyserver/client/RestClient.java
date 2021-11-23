package com.example.proxyserver.client;

import com.example.proxyserver.model.HttpMessage;

import com.example.proxyserver.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class RestClient implements ProxyClient {

    List<String> unmodificableHeaders = Arrays.asList("host","connection","name","content-length");
    List<String> ignoreResponseHeaders = Arrays.asList("content-encoding");

    @Autowired
    private HttpClient client;

    @Override
    public HttpMessage peek(HttpMessage message) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(message.getUrl()))
                .method(message.getMethod(), getBody(message.getBody()));
        this.addHeaders(message.getHeaders(),requestBuilder);
        HttpResponse<String> httpResponse = client.send(requestBuilder.build(),HttpResponse.BodyHandlers.ofString());
        return Mapper.toDto(httpResponse,ignoreResponseHeaders);
    }

    private void addHeaders(Map<String,String> headers, HttpRequest.Builder build) {
        headers.keySet().stream()
                .filter(e->!unmodificableHeaders.contains(e.toLowerCase()))
                .forEach(e->build.setHeader(e, headers.get(e)));
    }

    private HttpRequest.BodyPublisher getBody(String body) {

        return HttpRequest.BodyPublishers.ofString(body);
    }
}
