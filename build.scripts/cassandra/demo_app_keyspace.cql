create keyspace demo_app with replication = {
    'class':'NetworkTopologyStrategy',
    'dc1': 3
    };

use demo_app;

create table if not exists user_by_uuid
(
    uuid         text primary key,
    login        text,
    passwordHash text,
    groupId      text,
    status       text
);

create table if not exists user_by_login
(
    login        text primary key,
    passwordHash text
);

create table if not exists user_by_groupId
(
    uuid    text,
    groupId text,
    primary key ((groupId), uuid)
);