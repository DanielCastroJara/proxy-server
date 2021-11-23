package com.example.proxyserver.service;

import com.example.proxyserver.client.ProxyClient;
import com.example.proxyserver.model.HttpMessage;
import com.example.proxyserver.utils.HttpException;
import com.example.proxyserver.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;

@Service
public class ProxyService {

    @Autowired
    private ProxyClient proxyClient;

    @Autowired
    private LogsService logsService;

    public ResponseEntity<String> redirect(String targetUrl, Object body, HttpServletRequest httpServletRequest) {
        try {
            HttpMessage message = Mapper.toDto(targetUrl, body, httpServletRequest);
            HttpMessage response = proxyClient.peek(message);
            logsService.generateRequestLogs(message);
            logsService.generateResponseLogs(response);
            return ResponseEntity.status(response.isStatusCodeSatisfactory() || response.isStatusCodeRedirect() ? HttpStatus.FOUND : HttpStatus.NOT_ACCEPTABLE)
                    .location(response.getUriUrl())
                    .headers(response.geHttpHeaders())
                    .body(response.getBody());
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new HttpException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
}
