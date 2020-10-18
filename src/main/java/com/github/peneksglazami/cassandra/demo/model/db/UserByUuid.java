package com.github.peneksglazami.cassandra.demo.model.db;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "user_by_uuid")
public class UserByUuid {

    @PrimaryKey(value = "uuid")
    private String uuid;
    @Column(value = "login")
    private String login;
    @Column(value = "passwordHash")
    private String passwordHash;
    @Column(value = "groupId")
    private String groupId;
    @Column(value = "status")
    private String status;

    public UserByUuid() {
    }

    public UserByUuid(String uuid, String login, String passwordHash, String groupId, String status) {
        this.uuid = uuid;
        this.login = login;
        this.passwordHash = passwordHash;
        this.groupId = groupId;
        this.status = status;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
