#!/bin/bash
set -e
  # Проверяем, инициализировал ли уже кластер или нет.
  if [ ! -f /opt/dse/test_cluster_bootstrapped ]; then
    echo 'Setting up Test Cluster'

    echo '=> Starting Cassandra'
    /entrypoint.sh "$@" &
    cassandra_pid="$!"

    # Подождём пока порт 9042 (CQL) не будет доступен
    echo '=> Waiting for Cassandra to become available'
    /wait-for-it.sh -t 240 127.0.0.1:9042
    echo '=> Cassandra is available'

    # Создаём keyspace и таблицы
    echo '=> Ensuring keyspace is created'
    cqlsh -f /opt/init-scripts/demo_app_keyspace.cql 127.0.0.1 9042

    if [ -f /opt/init-scripts/user_by_uuid.csv ]; then
      # Заполняем таблицы тестовыми данными
      echo '=> Insert test data'
      cqlsh -e "COPY demo_app.user_by_uuid (uuid, login, passwordhash, groupId, status) from '/opt/init-scripts/user_by_uuid.csv' WITH DELIMITER=';'" 127.0.0.1 9042
      cqlsh -e "COPY demo_app.user_by_login (login, passwordhash) from '/opt/init-scripts/user_by_login.csv' WITH DELIMITER=';'" 127.0.0.1 9042
      cqlsh -e "COPY demo_app.user_by_groupId (uuid, groupId) from '/opt/init-scripts/user_by_groupId.csv' WITH DELIMITER=';'" 127.0.0.1 9042
    fi

    # Останавливаем экземпляр cassandra. См. последнюю строку файла, там мы запустим cassandra снова.
    echo '=> Shutting down Cassandra after bootstrapping'
    kill -s TERM "$cassandra_pid"

    set +e
    wait "$cassandra_pid"
    if [ $? -ne 143 ]; then
      echo >&2 'Test cluster setup failed'
      exit 1
    fi
    set -e

    # Создадим пустой файл, который будет указывать, что мы уже произвели инициализацию keyspace
    touch test_cluster_bootstrapped

    echo 'Test cluster has been setup, starting Cassandra normally'
  fi

# Запускаем скрипт инициализации Cassandra спритом из базового образа
exec /entrypoint.sh "$@"