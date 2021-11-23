package com.example.proxyserver.utils;

import com.example.proxyserver.model.HttpMessage;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Mapper {

    public static HttpMessage toDto(String targetUrl, Object body, HttpServletRequest request) {
        HttpMessage message = new HttpMessage();
        message.setBody(body);
        message.setHeaders(request);
        message.setUrl(targetUrl);
        message.setMethod(request.getMethod().toUpperCase());
        return message;
    }

    public static HttpMessage toDto(HttpResponse<String> httpResponse, List<String> ignoreHeaders) {
        HttpMessage message = new HttpMessage();
        message.setUrl(httpResponse.uri().toString());
        message.setBody(httpResponse.body());
        message.setMethod(null);
        message.setHttpStatus(httpResponse.statusCode());
        httpResponse.headers().map().keySet().stream()
                .filter(e->!ignoreHeaders.contains(e))
                .forEach(e->message.getHeaders().put(e, httpResponse.headers().map().get(e).get(0)));
        return message;
    }
}
