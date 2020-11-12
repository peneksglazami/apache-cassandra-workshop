package com.github.peneksglazami.cassandra.demo.services;

import com.github.peneksglazami.cassandra.demo.model.ShortUserInfo;
import com.github.peneksglazami.cassandra.demo.model.db.UserByGroupId;
import com.github.peneksglazami.cassandra.demo.model.db.UserByLogin;
import com.github.peneksglazami.cassandra.demo.model.db.UserByUuid;
import com.github.peneksglazami.cassandra.demo.model.rest.*;
import org.springframework.data.cassandra.core.CassandraBatchOperations;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;

@Service
public class CassandraStorageService {

    private final CassandraOperations cassandraOperations;

    public CassandraStorageService(CassandraOperations cassandraOperations) {
        this.cassandraOperations = cassandraOperations;
    }

    public GetUserResponse getUserByUUID(String uuid) {
        UserByUuid userByUuid = cassandraOperations.selectOneById(uuid, UserByUuid.class);

        if (userByUuid != null) {
            GetUserResponse getUserResponse = new GetUserResponse();
            getUserResponse.setUuid(userByUuid.getUuid());
            getUserResponse.setLogin(userByUuid.getLogin());
            getUserResponse.setGroupId(userByUuid.getGroupId());
            getUserResponse.setStatus(UserStatus.valueOf(userByUuid.getStatus()));
            return getUserResponse;
        }

        return null;
    }

    public ShortUserInfo getUserByLogin(String login) {
        UserByLogin userByLogin = cassandraOperations.selectOneById(login, UserByLogin.class);

        if (userByLogin != null) {
            ShortUserInfo shortUserInfo = new ShortUserInfo();
            shortUserInfo.setLogin(userByLogin.getLogin());
            shortUserInfo.setPasswordHash(userByLogin.getPasswordHash());
            return shortUserInfo;
        }

        return null;
    }

    public String createUser(CreateUserRequest request) {

        ShortUserInfo user = getUserByLogin(request.getLogin());
        if (user != null) {
            // учётная запись с указанным логином уже существует
            return null;
        }

        String uuid = UUID.randomUUID().toString();
        String passwordHash = DigestUtils.md5DigestAsHex(request.getPassword().getBytes());

        UserByLogin userByLogin = new UserByLogin(
                request.getLogin(),
                passwordHash
        );

        UserByUuid userByUuid = new UserByUuid(
                uuid,
                request.getLogin(),
                passwordHash,
                request.getGroupId(),
                request.getStatus().getValue()
        );

        UserByGroupId userByGroupId = new UserByGroupId(
                request.getGroupId(),
                uuid
        );

        cassandraOperations.batchOps().insert(userByLogin, userByUuid, userByGroupId).execute();

        return uuid;
    }

    public boolean deleteUser(String uuid) {
        UserByUuid userByUuid = cassandraOperations.selectOneById(uuid, UserByUuid.class);

        if (userByUuid != null) {
            cassandraOperations.batchOps().delete(
                    userByUuid,
                    new UserByLogin(userByUuid.getLogin()),
                    new UserByGroupId(userByUuid.getGroupId(), uuid)
            ).execute();

            return true;
        }

        return false;
    }

    public boolean updateUser(String uuid, UpdateUserRequest request) {
        UserByUuid oldUserByUuid = cassandraOperations.selectOneById(uuid, UserByUuid.class);
        if (oldUserByUuid != null) {
            String passwordHash = request.getPassword() != null ? DigestUtils.md5DigestAsHex(request.getPassword().getBytes()) : oldUserByUuid.getPasswordHash();

            UserByLogin userByLogin = new UserByLogin(
                    request.getLogin(),
                    passwordHash
            );

            UserByUuid userByUuid = new UserByUuid(
                    oldUserByUuid.getUuid(),
                    request.getLogin(),
                    passwordHash,
                    request.getGroupId(),
                    request.getStatus().getValue()
            );

            UserByGroupId userByGroupId = new UserByGroupId(
                    request.getGroupId(),
                    oldUserByUuid.getUuid()
            );

            CassandraBatchOperations cassandraBatchOperations = cassandraOperations.batchOps();
            cassandraBatchOperations.update(userByLogin, userByUuid);
            cassandraBatchOperations.insert(userByGroupId);
            if (!oldUserByUuid.getLogin().equals(request.getLogin())) {
                cassandraBatchOperations.delete(new UserByLogin(oldUserByUuid.getLogin()));
            }
            if (!oldUserByUuid.getGroupId().equals(request.getGroupId())) {
                cassandraBatchOperations.delete(new UserByGroupId(oldUserByUuid.getGroupId(), oldUserByUuid.getUuid()));
            }
            cassandraBatchOperations.execute();

            return true;
        }

        return false;
    }

    public void updateUserGroup(UserGroupUpdateRequest request) {
        // TODO: It is your homework!
    }
}
