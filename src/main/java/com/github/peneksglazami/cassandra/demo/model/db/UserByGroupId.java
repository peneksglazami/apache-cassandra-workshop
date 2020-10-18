package com.github.peneksglazami.cassandra.demo.model.db;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "user_by_groupId")
public class UserByGroupId {

    @PrimaryKey(value = "groupId")
    private String groupId;
    @Column(value = "uuid")
    private String uuid;

    public UserByGroupId() {
    }

    public UserByGroupId(String groupId, String uuid) {
        this.groupId = groupId;
        this.uuid = uuid;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
