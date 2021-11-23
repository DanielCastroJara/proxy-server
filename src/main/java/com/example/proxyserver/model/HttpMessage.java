package com.example.proxyserver.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.StreamSupport;

@Data
public class HttpMessage {

    private String url;
    private String body;
    private Map<String,String> headers = new HashMap<>();
    private String method;
    private Integer httpStatus;

    public void setHeaders(HttpServletRequest sourceRequest) {
        Iterable<String> iterable = () -> sourceRequest.getHeaderNames().asIterator();
        StreamSupport.stream(iterable.spliterator(), false)
                .forEach(e->this.headers.put(e, sourceRequest.getHeader(e)));
    }
    public void setBody(Object body) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonBody = body==null ? "{}" : objectMapper.writeValueAsString(body);
            this.body = jsonBody;
        }catch(Exception e) {
            this.body = "{}";
        }
    }

    public URI getUriUrl() throws URISyntaxException {
        return new URI(this.url);
    }

    public HttpHeaders geHttpHeaders() {
        HttpHeaders responseHeaders = new HttpHeaders();
        this.headers.keySet()
                .forEach(keyValue -> responseHeaders.add(keyValue,this.headers.get(keyValue) ));
        return responseHeaders;
    }

    public boolean isStatusCodeSatisfactory() {
        String code = this.httpStatus.toString().trim();
        return code.charAt(0) == '2';
    }

    public boolean isStatusCodeRedirect() {
        String code = this.httpStatus.toString().trim();
        return code.charAt(0) == '3';
    }
}
