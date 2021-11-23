package com.example.proxyserver.controller;

import com.example.proxyserver.service.ProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ProxyController {

    @Autowired
    private ProxyService proxyService;

    @RequestMapping(value = "/redirect-web")
    public ResponseEntity<?> redirect(@RequestParam String target, @RequestBody(required = false) Object body, HttpServletRequest request) {
        return proxyService.redirect(target, body, request);
    }
}
