package com.github.peneksglazami.cassandra.demo.model.db;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "user_by_login")
public class UserByLogin {

    @PrimaryKey(value = "login")
    private String login;
    @Column(value = "passwordHash")
    private String passwordHash;

    public UserByLogin() {
    }

    public UserByLogin(String login, String passwordHash) {
        this.login = login;
        this.passwordHash = passwordHash;
    }

    public UserByLogin(String login) {
        this.login = login;
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
}
