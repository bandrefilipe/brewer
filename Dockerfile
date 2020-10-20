FROM gradle:6.3-jdk11 AS builder
COPY --chown=gradle:gradle . /gradle
WORKDIR /gradle
RUN gradle build

FROM openjdk:11-slim
LABEL author="Andre Filipe Barranco"
LABEL email="b.andrefilipe@gmail.com"
COPY --from=builder /gradle/brewer-configuration/build/libs/*.jar /app/
WORKDIR /app
ENTRYPOINT java -jar *.jar
EXPOSE 8080
