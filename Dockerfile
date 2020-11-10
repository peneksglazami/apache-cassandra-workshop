FROM datastax/dse-server:6.8.4

COPY ["./build.scripts/cassandra/", "/opt/init-scripts/"]
COPY ["./build.scripts/cassandra/bootstrap.sh", "./build.scripts/cassandra/wait-for-it.sh", "/"]

# Переносы строк в файлах bootstrap.sh и wait-for-it.sh должны быть LF, а не CRFL.
# Поэтому на всякий случай выполним перекодирование файлов.
USER root
RUN set -x \
  && apt-get update -qq \
  && apt-get install dos2unix \
  && dos2unix /bootstrap.sh \
  && dos2unix /wait-for-it.sh \
  && apt-get clean all \
  && rm -rf /var/lib/{apt,dpkg,cache,log}/
USER dse

ENTRYPOINT ["/bootstrap.sh"]
CMD ["dse", "cassandra", "-f"]