FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
USER root
RUN --mount=type=cache,target=/.m2
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/generator.jar /usr/local/lib/generator.jar
COPY templates /templates
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/generator.jar"]
