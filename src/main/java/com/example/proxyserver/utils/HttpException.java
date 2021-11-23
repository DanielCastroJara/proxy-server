package com.example.proxyserver.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class HttpException extends RuntimeException{
    Logger logger = LogManager.getLogger(HttpException.class);
    private static final long serialVersionUID = -5266331095789475633L;

    private HttpStatus status;
    private Object data;

    public HttpException() {
        super();
    }

    public HttpException(String message) {
        super(message,null,false,false);
        logger.error(message);
    }

    public HttpException(HttpStatus status,String message) {
        this(message);
        this.status = status;
    }
}
