FROM azul/zulu-openjdk-alpine:11-jre
VOLUME /tmp
ARG JAVA_OPTS="-noverify -Xss512k"
ADD target/openwms-tms-routing-exec.jar app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar /app.jar
