FROM datastax/dse-server:6.8.4

COPY ["./build.scripts/cassandra/", "/opt/init-scripts/"]
COPY ["./build.scripts/cassandra/bootstrap.sh", "./build.scripts/cassandra/wait-for-it.sh", "/"]

ENTRYPOINT ["/bootstrap.sh"]
CMD ["dse", "cassandra", "-f"]