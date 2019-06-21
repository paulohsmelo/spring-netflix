# ==== BUILD and RUN =====
FROM maven:3.5.3-jdk-8-slim

RUN mkdir -p /opt/work
WORKDIR /opt/work

COPY ./startup.sh /opt/startup.sh
RUN chmod 777 /opt/startup.sh

ARG PATH_SERVICE
ARG SERVICE

ENV PATH_SERVICE=${PATH_SERVICE}
ENV SERVICE=${SERVICE}
ENV EUREKA_SERVER_URL=http://eureka:8761

COPY ./$PATH_SERVICE/target/$SERVICE.jar /opt/work/$SERVICE.jar

ENTRYPOINT sh /opt/startup.sh
