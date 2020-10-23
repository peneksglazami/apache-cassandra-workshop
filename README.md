# apache-cassandra-workshop
[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/peneksglazami/apache-cassandra-workshop)

Через `docker-compose up` поднимаются 4 контейнера: 3 контейнера содержат DataStax Enterprise 6.8.4 Cassandra, а один
DataStax Enterprise 6.8.4 Opscenter.

При запуске контейнера с именем `datastax-apache-cassandra1` выполняется создание keyspace `demo_app` и загрузка в таблицы
тестовых данных из csv-файлов.
Скрипты создания keyspace, csv-файлы и скрипт запуска Cassandra смотрите в каталоге `build.scripts/cassandra`.

![deployment](./img/deployment.png) 

## Домашнее задание
* Найдите ошибку в реализации метода `com.github.peneksglazami.cassandra.demo.services.CassandraStorageService.updateUser`.
* Реализуйте метод `com.github.peneksglazami.cassandra.demo.services.CassandraStorageService.updateUserGroup`.