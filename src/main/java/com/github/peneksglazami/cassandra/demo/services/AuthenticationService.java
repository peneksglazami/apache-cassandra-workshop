package com.github.peneksglazami.cassandra.demo.services;

import com.github.peneksglazami.cassandra.demo.model.ShortUserInfo;
import com.github.peneksglazami.cassandra.demo.model.rest.LoginResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;

@Service
public class AuthenticationService {

    private final CassandraStorageService cassandraStorageService;

    public AuthenticationService(CassandraStorageService cassandraStorageService) {
        this.cassandraStorageService = cassandraStorageService;
    }

    public LoginResponse authenticate(String login, String password) {
        ShortUserInfo shortUserInfo = cassandraStorageService.getUserByLogin(login);

        if (shortUserInfo == null) {
            return null;
        }

        String passwordHash = DigestUtils.md5DigestAsHex(password.getBytes());
        if (shortUserInfo.getPasswordHash().equals(passwordHash)) {
            LoginResponse resp = new LoginResponse();
            resp.setAuthToken(UUID.randomUUID().toString());
            return resp;
        } else {
            return null;
        }
    }
}