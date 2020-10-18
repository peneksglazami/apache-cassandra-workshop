package com.github.peneksglazami.cassandra.demo.controllers;

import com.github.peneksglazami.cassandra.demo.model.rest.LoginRequest;
import com.github.peneksglazami.cassandra.demo.model.rest.LoginResponse;
import com.github.peneksglazami.cassandra.demo.services.AuthenticationService;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpServletResponse resp) {
        LoginResponse result = authenticationService.authenticate(request.getLogin(), request.getPassword());
        if (result != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(result);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
