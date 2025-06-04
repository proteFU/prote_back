FROM gradle:8.5-jdk21 AS builder
WORKDIR /app

COPY build.gradle settings.gradle ./
COPY gradle ./gradle

RUN gradle dependencies || true

COPY . .

RUN gradle bootJar

FROM openjdk:21
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]