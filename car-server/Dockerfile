# ==== BUILD and RUN =====
FROM maven:3.5.3-jdk-8-slim

RUN mkdir -p /opt/work
WORKDIR /opt/work

ADD ./* /opt/work/

RUN rm -rf /opt/work/target/

EXPOSE 8081

ENV MAVEN_OPTS="-Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn"

RUN mvn package

ENTRYPOINT java -jar /opt/work/car-server.jar
