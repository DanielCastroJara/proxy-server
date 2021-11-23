package com.example.proxyserver.service;

import com.example.proxyserver.model.HttpMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class LogsService {

    Logger logger = LogManager.getLogger(LogsService.class);
    ObjectMapper objectMapper = new ObjectMapper();

    public void generateRequestLogs(HttpMessage request) throws JsonProcessingException {
        String[] params = new String[]{
                request.getUrl(),
                request.getMethod(),
                request.getBody(),
                objectMapper.writeValueAsString(request.getHeaders())};
        String responseLog = MessageFormat.format("REQUEST: Url({0}), METHOD: {1}, BODY: {2}, HEADERS: {3}.", params);
        logger.info(responseLog);
    }

    public void generateResponseLogs(HttpMessage response) throws JsonProcessingException {
        String[] params = new String[]{
                response.getUrl(),
                objectMapper.writeValueAsString(response.getHeaders())};
        String responseLog = MessageFormat.format("RESPONSE: Url({0}), HEADERS: {1}.", params);
        logger.info(responseLog);
    }
}
