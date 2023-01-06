
FROM gradle:jdk11 as gradleimage
COPY . /home/gradle/source
WORKDIR /home/gradle/source
RUN gradle build

FROM openjdk:11-jre-slim
COPY --from=gradleimage /home/gradle/source/build/libs/heroes-0.0.1-SNAPSHOT.jar /app/
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "heroes-0.0.1-SNAPSHOT.jar"]