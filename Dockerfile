FROM gradle:8.7-jdk21 AS builder

WORKDIR /app
COPY . .

RUN gradle shadowJar --no-daemon

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/app.jar .

CMD ["java", "-jar", "app.jar"]