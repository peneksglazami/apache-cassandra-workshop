package com.github.peneksglazami.cassandra.demo.services;

import com.github.peneksglazami.cassandra.demo.model.rest.CreateUserRequest;
import com.github.peneksglazami.cassandra.demo.model.rest.GetUserResponse;
import com.github.peneksglazami.cassandra.demo.model.rest.UpdateUserRequest;
import com.github.peneksglazami.cassandra.demo.model.rest.UserGroupUpdateRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final CassandraStorageService cassandraStorageService;

    public UserService(CassandraStorageService cassandraStorageService) {
        this.cassandraStorageService = cassandraStorageService;
    }

    public GetUserResponse getUserByUUID(String uuid) {
        return cassandraStorageService.getUserByUUID(uuid);
    }

    public boolean updateUser(String uuid, UpdateUserRequest request) {
        return cassandraStorageService.updateUser(uuid, request);
    }

    public boolean deleteUser(String uuid) {
        return cassandraStorageService.deleteUser(uuid);
    }

    public void updateUserGroup(UserGroupUpdateRequest request) {
        cassandraStorageService.updateUserGroup(request);
    }

    public String createUser(CreateUserRequest request) {
        return cassandraStorageService.createUser(request);
    }
}
